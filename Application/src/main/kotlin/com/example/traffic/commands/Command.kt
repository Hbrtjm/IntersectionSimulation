package com.example.traffic.commands

import com.example.traffic.simulation.IntersectionManager
import com.example.traffic.utils.Direction
import com.example.traffic.utils.TurnType
import io.ktor.util.debug.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.encodeToJsonElement

@Serializable
data class Command(val type: String, val vehicleId: String? = null, val startRoad: String? = null, val endRoad: String? = null, val direction: String? = null, val turnTypes: List<String>? = null)
