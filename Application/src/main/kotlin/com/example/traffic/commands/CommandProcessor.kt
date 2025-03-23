package com.example.traffic.commands

import com.example.traffic.simulation.IntersectionManager
import com.example.traffic.utils.Direction
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.encodeToJsonElement

// TODO - Get rid of the response wrapper, but for now it's good for debugging

class CommandProcessor {
    suspend fun runCommands(commands: Commands, call: ApplicationCall, intersectionManager: IntersectionManager) {
        val stepResults = mutableListOf<StepResponse>()

        // Loop through all commands and execute them
        for (command in commands.commands) {
            when (command.type) {
                "addVehicle" -> {
                    if (command.vehicleId != null && command.startRoad != null && command.endRoad != null) {
                        try {
                            val start = Direction.valueOf(command.startRoad.uppercase())
                            val end = Direction.valueOf(command.endRoad.uppercase())

                            intersectionManager.addVehicle(command.vehicleId, start, end)
                            stepResults.add(ListStepResponse(listOf("Vehicle ${command.vehicleId} added")))
                        } catch (e: Exception) {
                            stepResults.add(ListStepResponse(listOf("Error processing vehicle: ${command.vehicleId}")))
                        }
                    } else {
                        stepResults.add(ListStepResponse(listOf("Missing parameters for addVehicle")))
                    }
                }
                "step" -> {
                    try {
                        val leftVehicles = intersectionManager.simulationStep()
                        val jsonObjectOfCarIds = buildJsonObject {
                            put("carsLeft", Json.encodeToJsonElement(leftVehicles))
                        }
                        stepResults.add(JsonStepResponse(jsonObjectOfCarIds))
                    } catch (e: Exception) {
                        // This is not how I should be doing this, I think I know what causes the issue, but we know it's nothing serious,
                        // it makes steps failsafe, even if something would break
                        val empty = buildJsonObject {
                            put("carsLeft", Json.encodeToJsonElement(listOf<String>())) }
                        stepResults.add(JsonStepResponse(empty))
                    }
                }
                else -> {
                    stepResults.add(ListStepResponse(listOf("Unknown command type: ${command.type}")))
                }
            }
        }

        // Return all results as a bulk response
        call.respond(HttpStatusCode.OK, stepResults)
    }
}