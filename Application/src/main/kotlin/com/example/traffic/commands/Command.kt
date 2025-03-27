package com.example.traffic.commands

import kotlinx.serialization.Serializable

/**
 * Represents a command issued to control vehicles and roads within the traffic simulation.
 *
 * @property type The type of the command, valid commands are: "addVehicle", "step", "addRoad"
 * @property vehicleId The unique identifier of the vehicle to be added, has to be provided with aadVehicle command.
 * @property startRoad The direction of the starting road for the vehicle, has to be provided with aadVehicle command.
 * @property endRoad The direction of the end road for the vehicle, has to be provided with aadVehicle command.
 * @property direction Direction of the road has to be provided with addRoad command.
 * @property turnTypes TurnTypes for the newly added road has to be provided with addRoad command
 *
 */
@Serializable
data class Command(
    val type: String,
    val vehicleId: String? = null,
    val startRoad: String? = null,
    val endRoad: String? = null,
    val direction: String? = null,
    val turnTypes: List<String>? = null
)
