package com.example.traffic.commands

import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.Serializable

@Serializable
sealed class StepResponse

@Serializable
data class JsonStepResponse(val response: JsonObject) : StepResponse()

@Serializable
data class ListStepResponse(val response: List<String>) : StepResponse()