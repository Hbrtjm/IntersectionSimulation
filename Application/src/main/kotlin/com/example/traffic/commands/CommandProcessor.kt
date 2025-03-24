package com.example.traffic.commands

import com.example.traffic.simulation.IntersectionManager
import io.ktor.server.application.*

// TODO - Get rid of the response wrapper, but for now it's good for debugging

class CommandProcessor {
    suspend fun runCommands(commands: Commands, call: ApplicationCall, intersectionManager: IntersectionManager): List<StepResponse>
    {
        val stepResults = mutableListOf<StepResponse>()

        // Execute each command
        commands.commands.forEach { command ->
            CommandTranslator.translateCommand(
                command.type,
                command.startRoad,
                command.endRoad,
                command.vehicleId,
                command.direction,
                command.turnTypes,
                stepResults,
                intersectionManager)
        }

        return stepResults
    }
}