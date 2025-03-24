package com.example.traffic.commands

import com.example.traffic.simulation.IntersectionManager
import com.example.traffic.utils.Direction
import com.example.traffic.utils.TurnType
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.encodeToJsonElement

object CommandTranslator {
    fun translateCommand(
        type: String,
        startRoad: String?,
        endRoad: String?,
        vehicleId: String?,
        direction: String?,
        turnTypes: List<String>?,
        stepResults: MutableList<StepResponse>,
        intersectionManager: IntersectionManager
    )
    {
        when (type)
        {
            "addVehicle" -> handleAddVehicle(vehicleId, startRoad, endRoad, stepResults, intersectionManager)
            "step" -> handleStep(stepResults, intersectionManager)
            "addRoad" -> handleAddRoad(direction, turnTypes, stepResults, intersectionManager)
            else -> stepResults.add(ListStepResponse(listOf("Unknown command type: $type")))
        }
    }

    private fun handleAddRoad(direction: String?, turnTypes: List<String>?, stepResults: MutableList<StepResponse>, intersectionManager: IntersectionManager) {
        if(direction == null)
        {
            if(DebugModeController.isDebugModeOn())
            {
                stepResults.add(ListStepResponse(listOf("Direction not provided $direction")))
            }
            return
        }
        if(turnTypes == null)
        {
            if(DebugModeController.isDebugModeOn())
            {
                stepResults.add(ListStepResponse(listOf("TurnTypes not provided $turnTypes")))
            }
            return
        }
        val directionString: String = direction.toString()
        // Map the turn directions to an array, appends a road to the map of roads
        intersectionManager.addRoad(Direction.valueOf(directionString.uppercase()),turnTypes?.map { turnString -> TurnType.valueOf(turnString.uppercase()) })
        if(DebugModeController.isDebugModeOn())
        {
            stepResults.add(ListStepResponse(listOf("Added Road: direction: $direction and turnTypes: $turnTypes")))
        }
    }

    private fun handleAddVehicle(
        vehicleId: String?,
        startRoad: String?,
        endRoad: String?,
        stepResults: MutableList<StepResponse>,
        intersectionManager: IntersectionManager
    ) {
        if (vehicleId != null && startRoad != null && endRoad != null) {
            try {
                val start = Direction.valueOf(startRoad.uppercase())
                val end = Direction.valueOf(endRoad.uppercase())

                intersectionManager.addVehicle(vehicleId, start, end)
                if(DebugModeController.isDebugModeOn())
                {
                    stepResults.add(ListStepResponse(listOf("Vehicle $vehicleId added")))
                }
            } catch (e: Exception)
            {
                if(DebugModeController.isDebugModeOn())
                {
                    stepResults.add(ListStepResponse(listOf("Error processing vehicle: $vehicleId ${e.message}")))
                }
            }
        } else {
            if(DebugModeController.isDebugModeOn()) {
                stepResults.add(ListStepResponse(listOf("Missing parameters for addVehicle")))
            }
        }
    }

    private fun handleStep(
        stepResults: MutableList<StepResponse>,
        intersectionManager: IntersectionManager
    )
    {
        try
        {
            val leftVehicles = intersectionManager.simulationStep()
            val jsonObjectOfCarIds = buildJsonObject {
                put("carsLeft", Json.encodeToJsonElement(leftVehicles))
            }
            stepResults.add(JsonStepResponse(jsonObjectOfCarIds))
        }
        catch (e: Exception)
        {
            val empty = buildJsonObject {
                put("carsLeft", Json.encodeToJsonElement(listOf<String>()))
            }
            stepResults.add(JsonStepResponse(empty))
        }
    }
}