package com.example.traffic.commands

import com.example.traffic.simulation.IntersectionManager
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import java.io.File

class FileHandler(inputFile: String, outputFile: String) {

    private val intersectionManager = IntersectionManager()
    private val commandProcessor = CommandProcessor()
    private val inputFile = inputFile
    private val outputFile = outputFile

    fun executeFromFile() 
    {
        val commands = readAndParseJson(inputFile)

        val stepResults = mutableListOf<StepResponse>()
        commands.commands.forEach { command ->
            CommandTranslator.translateCommand(
                command.type,
                command.startRoad,
                command.endRoad,
                command.vehicleId,
                command.direction,
                command.turnTypes,
                stepResults,
                intersectionManager
            )
        }
        writeResult(stepResults)
    }

    private fun writeResult(stepResults: MutableList<StepResponse>) 
    {
        val jsonResult = jacksonObjectMapper().writeValueAsString(stepResults)

        File(outputFile).writeText(jsonResult)
    }

    private fun readAndParseJson(filePath: String): Commands 
    {
        val mapper = jacksonObjectMapper()
        mapper.registerKotlinModule()
        mapper.registerModule(JavaTimeModule())

        val jsonString: String = File(filePath).readText(Charsets.UTF_8)
        return mapper.readValue(jsonString)
    }
}
