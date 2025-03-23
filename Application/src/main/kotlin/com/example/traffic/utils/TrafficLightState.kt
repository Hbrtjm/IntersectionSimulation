package com.example.traffic.utils

enum class TrafficLightState {
    GREEN, YELLOW, RED
}

fun parseTrafficLight(trafficLightString: String): TrafficLightState? {
    return when (trafficLightString.lowercase()) {
        "green" -> TrafficLightState.GREEN
        "yellow" -> TrafficLightState.YELLOW
        "red" -> TrafficLightState.RED
        else -> null
    }
}