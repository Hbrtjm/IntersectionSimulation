package com.example.traffic.commands

import com.example.traffic.simulation.IntersectionController
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.nio.charset.Charset
import com.example.traffic.commands.CommandProcessor.runCommands
import com.example.traffic.commands.CommandTranslator.convertStepResponses
import com.example.traffic.commands.DebugModeController.isDebugModeOn

/**
 *
 * Handles reading and writing of traffic simulation commands from and to JSON files.
 *
 * This class reads command data from an input JSON file, processes it through the `IntersectionManager`,
 * and writes the simulation results to an output file.
 *
 * @param inputFileName The name of the input JSON file containing traffic simulation commands.
 * @param outputFileName The name of the output JSON file to store results (optional, defaults to null).
 *
 */
class FileHandler(private val inputFileName: String?, private var outputFileName: String? = null) {

    private val intersectionController = IntersectionController()
    private lateinit var cleanFileName: String
    /**
     *
     * Reads the input JSON file, processes the commands, and writes the results to an output file.
     *
     * This function:
     * - Cleans the filename to ensure it's a valid JSON file.
     * - Reads the command data from the file.
     * - Processes each command using `IntersectionManager`.
     * - Writes the results of command execution to an output file.
     *
     * @throws Exception if an error occurs during file reading, command processing, or writing.
     *
     */
    fun readData(): String {
        if(inputFileName == null)
        {
            return "File name not provided"
        }
        try {
            cleanFileName = makeJson(inputFileName)
            val json = File(cleanFileName).readText(Charset.defaultCharset())
            val commands: Commands = Json.decodeFromString(json)
            lateinit var results: List<StepResponse>
            for (command in commands.commands) {
                try {
                    val result = runCommands(commands, intersectionController)
                    results = result
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            writeData(results)
            return "Successfully wrote to file"
        } catch (e: Exception) {
            e.printStackTrace()
            return "An error has occurred $e"
        }
    }

    /**
     *
     * Ensures the filename has a proper `.json` extension.
     *
     * This method removes any existing file extensions and concatenates `.json` to avoid potential errors.
     *
     * @param filename The original filename.
     * @return A cleaned filename with a `.json` extension.
     *
     */
    private fun makeJson(filename: String): String
    {
        return filename.replace(".json","").replace(".","").trim() + ".json"
    }

    /**
     *
     * Writes the simulation results to an output JSON file.
     *
     * If no output file name is provided, a default name is generated by prefixing "results_" to input filename.
     *
     * @param results A list of StepResponses containing the results of command execution.
     *
     */
    // TODO - Simplify the code here
    private fun writeData(results: List<StepResponse>) {
        val finalResult = if (!isDebugModeOn()) StepStatusesResponse(convertStepResponses(results)) else results

        try {
            val fileName = outputFileName ?: "results_$cleanFileName".also { outputFileName = it }
            val jsonOutput = Json.encodeToString(finalResult)
            File(fileName).writeText(jsonOutput, Charset.defaultCharset())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}