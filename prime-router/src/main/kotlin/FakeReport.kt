package gov.cdc.prime.router

import com.github.javafaker.Faker
import java.text.SimpleDateFormat
import java.util.concurrent.TimeUnit
import java.util.Random

class FakeReport {
    companion object {
        private fun randomChoice(vararg choices: String): String {
            val random = Random()
            return choices[random.nextInt(choices.size)]
        }

        internal fun buildColumn(element: Element, findValueSet: (name: String) -> ValueSet?): String {
            val faker = Faker()
            val address = faker.address()
            val patientName = faker.name()

            return when (element.type) {
                Element.Type.CITY -> address.cityName()
                Element.Type.POSTAL_CODE -> address.zipCode().toString()
                Element.Type.TEXT -> {
                    when {
                        element.nameContains("lab_name") -> "Any lab USA"
                        element.nameContains("facility_name") -> "Any facility USA"
                        element.nameContains("equipment_model_id") ->
                            randomChoice("BinaxNOW COVID-19 Ag Card",
                                "BD Veritor System for Rapid Detection of SARS-CoV-2*")
                        element.nameContains("specimen_source_site_text") -> "Nasal"
                        else -> faker.lorem().characters(5, 10)
                    }
                }
                Element.Type.NUMBER -> faker.number().numberBetween(1, 10).toString()
                Element.Type.DATE -> {
                    val date = when {
                        element.nameContains("DOB") -> faker.date().birthday(0, 100)
                        else -> faker.date().past(10, TimeUnit.DAYS)
                    }
                    val formatter = SimpleDateFormat(Element.datePattern)
                    formatter.format(date)
                }
                Element.Type.DATETIME -> {
                    val date = faker.date().past(10, TimeUnit.DAYS)
                    val formatter = SimpleDateFormat(Element.datetimePattern)
                    formatter.format(date)
                }
                Element.Type.DURATION -> TODO()
                Element.Type.CODE -> {
                    val valueSet = findValueSet(element.valueSet ?: "")
                        ?: error("ValueSet ${element.valueSet} is not available}")
                    val possibleValues = valueSet.values.map { it.code }.toTypedArray()
                    randomChoice(*possibleValues)
                }
                Element.Type.HD -> {
                    "0.0.0.0.1"
                }
                Element.Type.ID -> faker.numerify("######")
                Element.Type.ID_CLIA -> faker.numerify("##D#######")  // Ex, 03D1021379
                Element.Type.ID_DLN -> faker.idNumber().valid()
                Element.Type.ID_SSN -> faker.idNumber().validSvSeSsn()
                Element.Type.ID_NPI -> faker.numerify("##########")
                Element.Type.STREET -> if (element.name.contains("2")) "" else address.streetAddress()
                Element.Type.STATE -> randomChoice("AZ", "FL", "PA")
                Element.Type.COUNTY -> "Any County"
                Element.Type.PERSON_NAME -> {
                    when {
                        element.nameContains("first") -> patientName.firstName()
                        element.nameContains("last") -> patientName.lastName()
                        element.nameContains("middle") -> patientName.firstName() // no middle name in faker
                        element.nameContains("suffix") -> randomChoice(patientName.suffix(), "")
                        else -> TODO()
                    }
                }
                Element.Type.TELEPHONE -> faker.numerify("##########") // faker.phoneNumber().cellPhone()
                Element.Type.EMAIL -> "${patientName.username()}@email.com"
                null -> error("Invalid element type for ${element.name}")
            }
        }

        private fun buildRow(schema: Schema): List<String> {
            return schema.elements.map { buildColumn(it, Metadata::findValueSet) }
        }

        fun build(schema: Schema, count: Int = 10, source: Source): Report {
            val rows = (0 until count).map { buildRow(schema) }.toList()
            return Report(schema, rows, listOf(source))
        }
    }
}