package gov.cdc.prime.router.serializers.datatests

import ca.uhn.hl7v2.DefaultHapiContext
import ca.uhn.hl7v2.parser.CanonicalModelClassFactory
import ca.uhn.hl7v2.parser.PipeParser
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import gov.cdc.prime.router.Metadata
import gov.cdc.prime.router.Report
import gov.cdc.prime.router.TestSource
import gov.cdc.prime.router.serializers.Hl7Serializer
import net.jcip.annotations.NotThreadSafe
import org.apache.commons.io.FilenameUtils
import org.apache.commons.io.filefilter.SuffixFileFilter
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.function.Executable
import java.io.File
import java.util.TimeZone
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue
import kotlin.test.fail

/**
 * Runs data comparison tests for HL7 ORU R01 messages based on files in the test folder.
 * This test takes each HL7 file and compares its data to the internal.csv companion file in the
 * same test folder.  For example:  for a file named CareEvolution-20200415-0001.hl7 the data will
 * be compared to the file CareEvolution-20200415-0001.internal.csv.  Internal CSV files can have an
 * optional header row and follow the internal schema used by the the ReportStream router.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
// This keeps this test class from running in parallel with other test classes and letting the time zone change
// affect other tests
@NotThreadSafe
class ObservationMessageTests {

    /**
     * The folder from the classpath that contains the test files
     */
    private val testFileDir = "/test_data_files/Hl7_ORU-R01"

    /**
     * The original timezone of the JVM
     */
    private val origDefaultTimeZone = TimeZone.getDefault()

    /**
     * Set the default timezone to GMT to match the build and deployment environments.
     */
    @BeforeAll
    fun setDefaultTimeZone() {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT0"))
    }

    /**
     * Reset the timezone back to the original
     */
    @AfterAll
    fun resetDefaultTimezone() {
        TimeZone.setDefault(origDefaultTimeZone)
    }

    /**
     * Generate individual unit tests for each test file in the test folder.
     * @return a list of dynamic unit tests
     */
    @TestFactory
    fun generateDataTests(): Collection<DynamicTest> {
        return getTestFiles(testFileDir).map {
            DynamicTest.dynamicTest("Test ${FilenameUtils.getBaseName(it)}", FileTest(it))
        }
    }

    /**
     * Gets a list of test files from the given [path].
     * @return a list of absolute pathnames to the test files
     */
    private fun getTestFiles(path: String): List<String> {
        val files = ArrayList<String>()
        val fullDirPath = this.javaClass.getResource(path)?.path
        if (!fullDirPath.isNullOrBlank()) {
            val dir = File(fullDirPath)
            if (dir.exists()) {
                val filenames = dir.list(SuffixFileFilter(".hl7"))
                filenames?.forEach { files.add("$fullDirPath/$it") }
                if (files.isEmpty()) fail("There are no HL7 files present in $fullDirPath")
            } else {
                fail("Directory $path does not exist in the classpath.")
            }
            files.forEach { println(it) }
        } else {
            fail("Unable to obtain the path to the test files.")
        }
        return files
    }

    /**
     * Perform the unit test for the given HL7 [hl7AbsolutePath].  This test will compare the number of provided reports
     * (e.g. HL7 batch files have multiple reports) and verifies all data elements match the expected values in the
     * related .internal file that exists in the same folder as the HL7 test file.
     *
     * Limitations: Date times in the HL7 data without a speficied time zone are bound by the JVM default timezone and hence
     * will generate an error against the GMT0 expected result.  GMT is the timezone of the build and deployment environments.
     */
    class FileTest(private val hl7AbsolutePath: String) : Executable {
        /**
         * The schema to use.
         */
        private val schemaName = "covid-19"

        /**
         * The HAPI HL7 parser.
         */
        private val parser: PipeParser

        /**
         * The HL7 serializer.
         */
        private val serializer: Hl7Serializer

        init {
            // Make sure we have some content on the given HL7 file
            assertTrue(File(hl7AbsolutePath).length() > 0)

            // Initialize the HL7 parser
            val hapiContext = DefaultHapiContext()
            val mcf = CanonicalModelClassFactory("2.5.1")
            hapiContext.modelClassFactory = mcf
            parser = hapiContext.pipeParser

            // Setup the HL7 serializer
            val metadata = Metadata("./metadata")
            serializer = Hl7Serializer(metadata)
        }

        override fun execute() {
            val expectedResultAbsolutePath = "${FilenameUtils.removeExtension(hl7AbsolutePath)}.internal.csv"
            val testFilename = FilenameUtils.getName(hl7AbsolutePath)

            println("Testing file $testFilename ...")
            if (File(expectedResultAbsolutePath).exists()) {
                val report = getReport()
                val expectedResult = readExpectedResult(expectedResultAbsolutePath)
                compareToExpected(report, expectedResult)
                assertTrue(true)
            } else {
                fail("The expected data file $expectedResultAbsolutePath was not found for this test.")
            }
            println("PASSED: $testFilename")
            println("--------------------------------------------------------")
        }

        /**
         * Get the report for the HL7 file and check for errors.
         * @return the HL7 report
         */
        private fun getReport(): Report {
            val result = serializer.readExternal(schemaName, File(hl7AbsolutePath).inputStream(), TestSource)
            val filename = FilenameUtils.getName(hl7AbsolutePath)
            assertNotNull(result)
            assertNotNull(result.report)

            if (result.errors.isNotEmpty()) {
                println("HL7 file $filename has ${result.errors.size} HL7 decoding errors:")
                result.errors.forEach { println("   SCHEMA ERROR: ${it.details}") }
            }
            if (result.warnings.isNotEmpty()) {
                println("HL7 file $filename has ${result.warnings.size} HL7 decoding warnings:")
                result.warnings.forEach { println("   SCHEMA WARNING: ${it.details}") }
            }
            assertTrue(result.errors.isEmpty(), "There were data errors in the HL7 file.")
            return result.report!!
        }

        /**
         * Read the file [expectedFileAbsolutePath] that has the expected result.
         * @return a list of data rows
         */
        private fun readExpectedResult(expectedFileAbsolutePath: String): List<List<String>> {
            val file = File(expectedFileAbsolutePath)
            return csvReader().readAll(file)
        }

        /**
         * Compare the data in the [actual] report to the data in the [expected] report.
         */
        private fun compareToExpected(actual: Report, expected: List<List<String>>) {
            assertTrue(actual.schema.elements.isNotEmpty())

            // Is there a header row in the expected file?
            var expectedHasHeader = false
            var expectedSize = expected.size
            val firstColName = actual.schema.elements[0].name
            if (expected.size > 0 && expected[0][0] == firstColName) {
                expectedHasHeader = true
                expectedSize--
            }

            // Check the number of reports
            assertEquals(actual.itemCount, expectedSize, "Number of reports does not match.")

            // Now check the data in each report.
            val errorMsgs = ArrayList<String>()
            val warningMsgs = ArrayList<String>()

            for (i in 0 until actual.itemCount) {
                val actualRow = actual.getRow(i)
                val expectedRowIndex = if (expectedHasHeader) i + 1 else i
                val expectedRow = expected[expectedRowIndex]
                assertEquals(actualRow.size, expectedRow.size, "Incorrect number of columns in data for report #$i.")
                for (j in actualRow.indices) {
                    val colName = actual.schema.elements[j].name

                    // We want to error on differences when the expected data is not empty.
                    if (!expectedRow[j].isNullOrBlank() && actualRow[j].trim() != expectedRow[j].trim()) {
                        errorMsgs.add(
                            "   DATA ERROR: Data value does not match in report $i column #${j + 1}, '$colName'.  " +
                                "Expected: '${expectedRow[j].trim()}', Actual: '${actualRow[j].trim()}'"
                        )
                    } else if (expectedRow[j].trim().isEmpty() && actualRow[j].trim().isNotEmpty()) {
                        warningMsgs.add(
                            "   DATA WARNING: Actual data has value in report" +
                                " $i column #$${j + 1}, '$colName', but no expected value.  " +
                                "Actual: '${actualRow[j].trim()}'"
                        )
                    }
                }
            }
            // Add the errors and warnings to the assert message, so they show up in the build results.
            assertTrue(
                errorMsgs.size == 0,
                "There were ${errorMsgs.size} incorrect data value(s) detected with ${warningMsgs.size} warning(s)\n" +
                    errorMsgs.joinToString("\n") + warningMsgs.joinToString("\n")
            )
            // Print the warning messages if any
            if (errorMsgs.size == 0 && warningMsgs.size > 0) println(warningMsgs.joinToString("\n"))
        }
    }
}