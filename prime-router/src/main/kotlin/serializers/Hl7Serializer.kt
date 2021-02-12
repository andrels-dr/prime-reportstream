package gov.cdc.prime.router.serializers

import ca.uhn.hl7v2.DefaultHapiContext
import ca.uhn.hl7v2.HL7Exception
import ca.uhn.hl7v2.model.v251.message.ORU_R01
import ca.uhn.hl7v2.parser.CanonicalModelClassFactory
import ca.uhn.hl7v2.util.Terser
import gov.cdc.prime.router.Element
import gov.cdc.prime.router.ElementAndValue
import gov.cdc.prime.router.Mapper
import gov.cdc.prime.router.Metadata
import gov.cdc.prime.router.Report
import gov.cdc.prime.router.Schema
import gov.cdc.prime.router.ValueSet
import java.io.OutputStream
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Properties

class Hl7Serializer(val metadata: Metadata) {
    private val softwareVendorOrganization = "Centers for Disease Control and Prevention"
    private val softwareProductName = "PRIME Data Hub"
    private val hl7SegmentDelimiter: String = "\r"

    private val hapiContext = DefaultHapiContext()
    private val buildVersion: String
    private val buildDate: String
    private val formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss")

    init {
        val buildProperties = Properties()
        val propFileStream = this::class.java.classLoader.getResourceAsStream("build.properties")
            ?: error("Could not find the properties file")
        propFileStream.use {
            buildProperties.load(it)
            buildVersion = buildProperties.getProperty("buildVersion", "0.0.0.0")
            buildDate = buildProperties.getProperty("buildDate", "20200101")
        }
        hapiContext.modelClassFactory = CanonicalModelClassFactory("2.5.1")
    }

    /**
     * Write a report with a single item
     */
    fun write(report: Report, outputStream: OutputStream) {
        if (report.itemCount != 1)
            error("Internal Error: multiple item report cannot be written as a single HL7 message")
        val message = createMessage(report, 0)
        outputStream.write(message.toByteArray())
    }

    /**
     * Write a report with BHS and FHS segments and multiple items
     */
    fun writeBatch(report: Report, outputStream: OutputStream) {
        // Dev Note: HAPI doesn't support a batch of messages, so this code creates
        // these segments by hand
        outputStream.write(createHeaders(report).toByteArray())
        report.itemIndices.map {
            val message = createMessage(report, it)
            outputStream.write(message.toByteArray())
        }
        outputStream.write(createFooters(report).toByteArray())
    }

    /*
     * Read in a file
     */
    fun convertBatchMessagesToMap(message: String, schema: Schema): Map<String, List<String>> {
        val mappedRows: MutableMap<String, MutableList<String>> = mutableMapOf()
        val reg = "(\r|\n)".toRegex()
        val cleanedMessage = reg.replace(message, "\r")
        // todo: check for the segments that need to be removed because HAPI doesn't support batching
        // todo: remove FHS
        // todo: remove BHS
        // todo: remove BTS (but preserve batch count so we can validate)
        // todo: remove FTS
        // todo: loop each line (message split on \r)
        val messageLines = cleanedMessage.split("\r")
        val nextMessage = StringBuilder()

        fun deconstructStringMessage() {
            println("Examining:\n$nextMessage")
            val parsedMessage = convertMessageToMap(nextMessage.toString(), schema)
            nextMessage.clear()
            parsedMessage.forEach { (k, v) ->
                if (!mappedRows.containsKey(k))
                    mappedRows[k] = mutableListOf()

                mappedRows[k]?.addAll(v)
            }
        }

        messageLines.forEach {
            if (it.startsWith("FHS"))
                return@forEach
            if (it.startsWith("BHS"))
                return@forEach
            if (it.startsWith("BTS"))
                return@forEach
            if (it.startsWith("FTS"))
                return@forEach

            if (nextMessage.isNotEmpty() && it.startsWith("MSH")) {
                deconstructStringMessage()
            }
            nextMessage.append("$it\r")
        }

        // catch the last message
        if (nextMessage.isNotEmpty()) {
            deconstructStringMessage()
        }

        return mappedRows.toMap()
    }

    fun convertMessageToMap(message: String, schema: Schema): Map<String, List<String>> {
        // key of the map is the column header, list is the values in the column
        val mappedRows: MutableMap<String, MutableList<String>> = mutableMapOf()
        // todo: for each segment, loop through the elements (i.e. MSH-3-1)
        // todo: find a matching schema element that maps to the segment element
        // todo: add each value to the mapped rows collection
        val mcf = CanonicalModelClassFactory("2.5.1")
        hapiContext.modelClassFactory = mcf
        val parser = hapiContext.pipeParser
        val reg = "(\r|\n)".toRegex()
        val cleanedMessage = reg.replace(message, "\r")
        val hapiMsg = parser.parse(cleanedMessage)
        val terser = Terser(hapiMsg)
        schema.elements.forEach {
            if (it.hl7Field.isNullOrEmpty() && it.hl7OutputFields.isNullOrEmpty())
                return@forEach
            if (!mappedRows.containsKey(it.name))
                mappedRows[it.name] = mutableListOf()
            if (!it.hl7Field.isNullOrEmpty()) {
                val terserSpec = if (it.hl7Field.startsWith("MSH")) {
                    "/${it.hl7Field}"
                } else {
                    "/.${it.hl7Field}"
                }
                val parsedValue = try { terser.get(terserSpec) } catch (_: HL7Exception) { "Exception for $terserSpec" }
                // add the rows
                mappedRows[it.name]?.add(parsedValue ?: "Blank for $terserSpec")
            } else {
                it.hl7OutputFields?.forEach { h ->
                    val terserSpec = if (h.startsWith("MSH")) {
                        "/$h"
                    } else {
                        "/.$h"
                    }
                    val parsedValue = try {
                        terser.get(terserSpec)
                    } catch (_: HL7Exception) {
                        "Exception for $terserSpec"
                    }
                    // add the rows
                    mappedRows[it.name]?.add(parsedValue ?: "Blank for $terserSpec")
                }
            }
        }
        return mappedRows.toMap()
    }

    internal fun createMessage(report: Report, row: Int): String {
        val message = ORU_R01()
        message.initQuickstart("ORU", "R01", "D")
        buildMessage(message, report, row)
        hapiContext.modelClassFactory = CanonicalModelClassFactory("2.5.1")
        return hapiContext.pipeParser.encode(message)
    }

    private fun buildMessage(message: ORU_R01, report: Report, row: Int, processingId: String = "D") {
        var aoeSequence = 1
        val terser = Terser(message)
        setLiterals(terser)
        // serialize the rest of the elements
        report.schema.elements.forEach { element ->
            val value = report.getString(row, element.name) ?: return@forEach

            if (element.hl7OutputFields != null) {
                element.hl7OutputFields.forEach { hl7Field ->
                    setComponent(terser, element, hl7Field, value)
                }
            } else if (element.hl7Field == "AOE" && element.type == Element.Type.NUMBER) {
                if (value.isNotBlank()) {
                    val units = report.getString(row, "${element.name}_units")
                    val date = report.getString(row, "specimen_collection_date_time") ?: ""
                    setAOE(terser, element, aoeSequence++, date, value, units)
                }
            } else if (element.hl7Field == "AOE") {
                if (value.isNotBlank()) {
                    val date = report.getString(row, "specimen_collection_date_time") ?: ""
                    setAOE(terser, element, aoeSequence++, date, value)
                }
            } else if (element.hl7Field == "NTE-3") {
                setNote(terser, value)
            } else if (element.hl7Field == "MSH-7") {
                setComponent(terser, element, "MSH-7", formatter.format(report.createdDateTime))
            } else if (element.hl7Field == "MSH-11") {
                setComponent(terser, element, "MSH-11", processingId)
            } else if (element.hl7Field != null && element.mapperRef != null && element.type == Element.Type.TABLE) {
                setComponentForTable(terser, element, report, row)
            } else if (element.hl7Field != null) {
                setComponent(terser, element, element.hl7Field, value)
            }
        }
    }

    private fun setComponentForTable(terser: Terser, element: Element, report: Report, row: Int) {
        val lookupValues = mutableMapOf<String, String>()
        val pathSpec = formPathSpec(element.hl7Field!!)
        val mapper: Mapper? = element.mapperRef
        val args = element.mapperArgs ?: emptyList()
        val valueNames = mapper?.valueNames(element, args)
        report.schema.elements.forEach {
            lookupValues[it.name] = report.getString(row, it.name) ?: ""
        }
        val valuesForMapper = valueNames?.map { elementName ->
            val valueElement = report.schema.findElement(elementName)
                ?: error(
                    "Schema Error: Could not find element '$elementName' for mapper " +
                        "'${mapper.name}' from '${element.name}'."
                )
            val value = lookupValues[elementName]
                ?: error("Schema Error: No mapper input for $elementName")
            ElementAndValue(valueElement, value)
        }
        if (valuesForMapper == null) {
            terser.set(pathSpec, "")
        } else {
            terser.set(pathSpec, mapper.apply(element, args, valuesForMapper) ?: "")
        }
    }

    private fun setComponent(terser: Terser, element: Element, hl7Field: String, value: String) {
        val pathSpec = formPathSpec(hl7Field)
        when (element.type) {
            Element.Type.ID_CLIA -> {
                if (value.isNotEmpty()) {
                    terser.set(pathSpec, value)
                    terser.set(nextComponent(pathSpec), "CLIA")
                }
            }
            Element.Type.HD -> {
                if (value.isNotEmpty()) {
                    val hd = Element.parseHD(value)
                    if (hd.universalId != null && hd.universalIdSystem != null) {
                        terser.set("$pathSpec-1", hd.name)
                        terser.set("$pathSpec-2", hd.universalId)
                        terser.set("$pathSpec-3", hd.universalIdSystem)
                    } else {
                        terser.set(pathSpec, hd.name)
                    }
                }
            }
            Element.Type.EI -> {
                if (value.isNotEmpty()) {
                    val ei = Element.parseEI(value)
                    if (ei.universalId != null && ei.universalIdSystem != null) {
                        terser.set("$pathSpec-1", ei.name)
                        terser.set("$pathSpec-2", ei.namespace)
                        terser.set("$pathSpec-3", ei.universalId)
                        terser.set("$pathSpec-4", ei.universalIdSystem)
                    } else {
                        terser.set(pathSpec, ei.name)
                    }
                }
            }
            Element.Type.CODE -> setCodeComponent(terser, value, pathSpec, element.valueSet)
            Element.Type.TELEPHONE -> {
                if (value.isNotEmpty()) {
                    setTelephoneComponent(terser, value, pathSpec, element)
                } else {
                    terser.set(pathSpec, "") // Not at all sure what to do here.
                }
            }
            Element.Type.POSTAL_CODE -> setPostalComponent(terser, value, pathSpec, element)
            else -> terser.set(pathSpec, value)
        }
    }

    private fun setCodeComponent(terser: Terser, value: String, pathSpec: String, valueSetName: String?) {
        if (valueSetName == null) error("Schema Error: Missing valueSet for '$pathSpec'")
        val valueSet = metadata.findValueSet(valueSetName)
            ?: error("Schema Error: Cannot find '$valueSetName'")
        when (valueSet.system) {
            ValueSet.SetSystem.HL7,
            ValueSet.SetSystem.LOINC,
            ValueSet.SetSystem.UCUM,
            ValueSet.SetSystem.SNOMED_CT -> {
                // if it is a component spec then set all sub-components
                if (isField(pathSpec)) {
                    if (value.isNotEmpty()) {
                        terser.set("$pathSpec-1", value)
                        terser.set("$pathSpec-2", valueSet.toDisplayFromCode(value))
                        terser.set("$pathSpec-3", valueSet.systemCode)
                        valueSet.toVersionFromCode(value)?.let {
                            terser.set("$pathSpec-7", it)
                        }
                    }
                } else {
                    terser.set(pathSpec, value)
                }
            }
            else -> {
                terser.set(pathSpec, value)
            }
        }
    }

    private fun setTelephoneComponent(terser: Terser, value: String, pathSpec: String, element: Element) {
        val parts = value.split(Element.phoneDelimiter)
        val areaCode = parts[0].substring(0, 3)
        val local = parts[0].substring(3)
        val country = parts[1]
        val extension = parts[2]

        terser.set(buildComponent(pathSpec, 2), if (element.nameContains("patient")) "PRN" else "WPN")
        terser.set(buildComponent(pathSpec, 5), country)
        terser.set(buildComponent(pathSpec, 6), areaCode)
        terser.set(buildComponent(pathSpec, 7), local)
        if (extension.isNotEmpty()) terser.set(buildComponent(pathSpec, 8), extension)
    }

    private fun setPostalComponent(terser: Terser, value: String, pathSpec: String, element: Element) {
        val zipFive = element.toFormatted(value, Element.zipFiveToken)
        terser.set(pathSpec, zipFive)
    }

    private fun setAOE(
        terser: Terser,
        element: Element,
        aoeRep: Int,
        date: String,
        value: String,
        units: String? = null
    ) {
        terser.set(formPathSpec("OBX-1", aoeRep), (aoeRep + 1).toString())
        terser.set(formPathSpec("OBX-2", aoeRep), "CWE")

        val aoeQuestion = element.hl7AOEQuestion
            ?: error("Schema Error: missing hl7AOEQuestion for '${element.name}'")
        setCodeComponent(terser, aoeQuestion, formPathSpec("OBX-3", aoeRep), "covid-19/aoe")

        when (element.type) {
            Element.Type.CODE -> setCodeComponent(terser, value, formPathSpec("OBX-5", aoeRep), element.valueSet)
            Element.Type.NUMBER -> {
                if (element.name != "patient_age") TODO("support other types of AOE numbers")
                if (units == null) error("Schema Error: expected age units")
                setComponent(terser, element, formPathSpec("OBX-5", aoeRep), value)
                setCodeComponent(terser, units, formPathSpec("OBX-6", aoeRep), "patient_age_units")
            }
            else -> setComponent(terser, element, formPathSpec("OBX-5", aoeRep), value)
        }

        terser.set(formPathSpec("OBX-11", aoeRep), "F")
        terser.set(formPathSpec("OBX-14", aoeRep), date)
        terser.set(formPathSpec("OBX-29", aoeRep), "QST")
    }

    private fun setNote(terser: Terser, value: String) {
        if (value.isBlank()) return
        terser.set(formPathSpec("NTE-3"), value)
        terser.set(formPathSpec("NTE-4-1"), "RE")
        terser.set(formPathSpec("NTE-4-2"), "Remark")
        terser.set(formPathSpec("NTE-4-3"), "HL70364")
        terser.set(formPathSpec("NTE-4-7"), "2.5.1")
    }

    private fun setLiterals(terser: Terser) {
        // Value that NIST requires (although # is not part of 2.5.1)
        terser.set("MSH-5", metadata.receivingApplication)
        terser.set("MSH-6", metadata.receivingFacility)
        terser.set("MSH-15", "NE")
        terser.set("MSH-16", "NE")
        terser.set("MSH-12", "2.5.1")
        terser.set("MSH-17", "USA")

        terser.set("SFT-1", softwareVendorOrganization)
        terser.set("SFT-2", buildVersion)
        terser.set("SFT-3", softwareProductName)
        terser.set("SFT-4", buildVersion)
        terser.set("SFT-6", buildDate)

        terser.set("/PATIENT_RESULT/PATIENT/PID-1", "1")

        terser.set("/PATIENT_RESULT/ORDER_OBSERVATION/ORC-1", "RE")

        terser.set("/PATIENT_RESULT/ORDER_OBSERVATION/OBR-1", "1")

        terser.set("/PATIENT_RESULT/ORDER_OBSERVATION/SPECIMEN/SPM-1", "1")

        terser.set("/PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION/OBX-1", "1")
        terser.set("/PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION/OBX-2", "CWE")
    }

    private fun createHeaders(report: Report): String {
        val encodingCharacters = "^~\\&"
        val sendingApp = formatHD(Element.parseHD(report.getString(0, "sending_application") ?: ""))
        val sendingFacility = formatHD(Element.parseHD(report.getString(0, "sending_application") ?: ""))
        val receivingApp = formatHD(Element.parseHD(report.getString(0, "receiving_application") ?: ""))
        val receivingFacility = formatHD(Element.parseHD(report.getString(0, "receiving_facility") ?: ""))

        return "FHS|$encodingCharacters|" +
            "$sendingApp|" +
            "$sendingFacility|" +
            "$receivingApp|" +
            "$receivingFacility|" +
            nowTimestamp() +
            hl7SegmentDelimiter +
            "BHS|$encodingCharacters|" +
            "$sendingApp|" +
            "$sendingFacility|" +
            "$receivingApp|" +
            "$receivingFacility|" +
            nowTimestamp() +
            hl7SegmentDelimiter
    }

    private fun createFooters(report: Report): String {
        return "BTS|${report.itemCount}$hl7SegmentDelimiter" +
            "FTS|1$hl7SegmentDelimiter"
    }

    private fun nowTimestamp(): String {
        val timestamp = OffsetDateTime.now(ZoneId.systemDefault())
        return Element.datetimeFormatter.format(timestamp)
    }

    private fun buildComponent(spec: String, component: Int = 1): String {
        if (!isField(spec)) error("Not a component path spec")
        return "$spec-$component"
    }

    private fun isField(spec: String): Boolean {
        val pattern = Regex("[A-Z][A-Z][A-Z]-[0-9]+$")
        return pattern.containsMatchIn(spec)
    }

    private fun nextComponent(spec: String, increment: Int = 1): String {
        val componentPattern = Regex("[A-Z][A-Z][A-Z]-[0-9]+-([0-9]+)$")
        componentPattern.find(spec)?.groups?.get(1)?.let {
            val nextComponent = it.value.toInt() + increment
            return spec.replaceRange(it.range, nextComponent.toString())
        }
        val subComponentPattern = Regex("[A-Z][A-Z][A-Z]-[0-9]+-[0-9]+-([0-9]+)$")
        subComponentPattern.find(spec)?.groups?.get(1)?.let {
            val nextComponent = it.value.toInt() + increment
            return spec.replaceRange(it.range, nextComponent.toString())
        }
        error("Did match on component or subcomponent")
    }

    private fun formPathSpec(spec: String, rep: Int? = null): String {
        val segment = spec.substring(0, 3)
        val components = spec.substring(3)
        val repSpec = rep?.let { "($rep)" } ?: ""
        return when (segment) {
            "OBR" -> "/PATIENT_RESULT/ORDER_OBSERVATION/OBR$components"
            "ORC" -> "/PATIENT_RESULT/ORDER_OBSERVATION/ORC$components"
            "SPM" -> "/PATIENT_RESULT/ORDER_OBSERVATION/SPECIMEN/SPM$components"
            "PID" -> "/PATIENT_RESULT/PATIENT/PID$components"
            "OBX" -> "/PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION$repSpec/OBX$components"
            "NTE" -> "/PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION/NTE$components"
            else -> spec
        }
    }

    private fun formatHD(hdFields: Element.HDFields, separator: String = "^"): String {
        return if (hdFields.universalId != null && hdFields.universalIdSystem != null) {
            "${hdFields.name}$separator${hdFields.universalId}$separator${hdFields.universalIdSystem}"
        } else {
            hdFields.name
        }
    }

    private fun formatEI(eiFields: Element.EIFields, separator: String = "^"): String {
        return if (eiFields.namespace != null && eiFields.universalId != null && eiFields.universalIdSystem != null) {
            "${eiFields.name}$separator${eiFields.namespace}" +
                "$separator${eiFields.universalId}$separator${eiFields.universalIdSystem}"
        } else {
            eiFields.name
        }
    }
}