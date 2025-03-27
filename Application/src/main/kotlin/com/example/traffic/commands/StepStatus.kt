package com.example.traffic.commands

import kotlinx.serialization.Serializable

/**
 *
 * Represents the status of vehicles that left the intersection after a step.
 *
 * @property leftVehicles A list of vehicle IDs that crossed the intersection.
 *
 */
@Serializable
data class StepStatus(val leftVehicles: List<String>)