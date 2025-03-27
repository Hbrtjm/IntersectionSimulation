package com.example.traffic.commands

import com.example.traffic.simulation.IntersectionManager
import com.example.traffic.utils.Direction
import com.example.traffic.utils.TurnType
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.encodeToJsonElement

/**
 *
 * Translates and processes commands.
 *
 * This object is responsible for handling various commands such as adding vehicles, adding roads,
 * and executing simulation steps. It interacts with the `IntersectionManager` to modify the simulation state
 * and records the results in `stepResults`.
 *
 */
object CommandTranslator {
    /**
     *
     * Translates a given command into an action within the traffic simulation.
     *
     * @param type The type of the command, valid commands are: "addVehicle", "step", "addRoad"
     * @param vehicleId The unique identifier of the vehicle to be added, has to be provided with aadVehicle command.
     * @param startRoad The direction of the starting road for the vehicle, has to be provided with aadVehicle command.
     * @param endRoad The direction of the end road for the vehicle, has to be provided with aadVehicle command.
     * @param direction Direction of the road has to be provided with addRoad command.
     * @param turnTypes TurnTypes for the newly added road has to be provided with addRoad command
     * @param stepResults A list to store responses generated by the command execution.
     * @param intersectionManager The manager that handles the intersection simulation.
     *
    */
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

    /**
     *
     * Adds a new road to the simulation.
     *
     * @param direction The direction of the road (must not be null).
     * @param turnTypes The turn types allowed on the road (must not be null).
     * @param stepResults A list to store responses generated by the command execution. Adds the response only in debug mode.
     * @param intersectionManager The manager that modifies the intersection.
     *
     */
    private fun handleAddRoad(direction: String?, turnTypes: List<String>?, stepResults: MutableList<StepResponse>, intersectionManager: IntersectionManager) {
        if(direction == null)
        {
            if(DebugModeController.isDebugModeOn())
            {
                stepResults.add(ListStepResponse(listOf("Direction not provided")))
            }
            return
        }
        if(turnTypes == null)
        {
            if(DebugModeController.isDebugModeOn())
            {
                stepResults.add(ListStepResponse(listOf("TurnTypes not provided")))
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
    /**
     *
     * Handles the addition of a vehicle to the simulation.
     *
     * @param vehicleId The unique identifier of the vehicle.
     * @param startRoad The starting direction of the vehicle.
     * @param endRoad The destination direction of the vehicle.
     * @param stepResults A list to store responses generated by the command execution. Addss the response only in debug mode.
     * @param intersectionManager The manager that modifies the intersection.
     *
     */
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

    /**
     *
     * Handles the execution of a simulation step, moving vehicles as needed.
     *
     * @param stepResults A list to store responses generated by the simulation step. Always a new value added to it after a step execution.
     * @param intersectionManager The manager that modifies the intersection
     *
     */
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