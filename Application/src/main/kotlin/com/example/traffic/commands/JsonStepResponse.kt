package com.example.traffic.commands

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

/**
 *
 * Debug response in JSON for step command
 *
 */
@Serializable
data class JsonStepResponse(val response: JsonObject) : StepResponse()