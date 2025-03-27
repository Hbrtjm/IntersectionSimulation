package com.example.traffic.commands

import kotlinx.serialization.Serializable

// TODO - make it into one class

/**
 *
 * Represents the response containing multiple step statuses in the traffic simulation.
 *
 * @property stepStatuses A list of StepStatuses, each containing information about vehicles that left.
 *
 */
@Serializable
data class StepStatusesResponse(val stepStatuses: List<StepStatus>)