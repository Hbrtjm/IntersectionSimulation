package com.example.traffic.commands

import kotlinx.serialization.Serializable

/**
 *
 * Debug response in JSON for commands other than step
 *
 */
@Serializable
data class ListStepResponse(val response: List<String>) : StepResponse()