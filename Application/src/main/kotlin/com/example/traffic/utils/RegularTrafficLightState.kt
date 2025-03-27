package com.example.traffic.utils

/**
 * Specifies the possible states of a regular traffic light.
 *
 * - GREEN: Vehicles are allowed to pass through.
 * - RED_YELLOW: A transitional state between RED and GREEN, indicating that the light is about to turn GREEN.
 * - YELLOW: A transitional state between GREEN and RED, warning that the light will soon turn RED.
 * - RED: Vehicles must stop, as passage is not allowed.
 * - BROKEN: Represents an inactive or malfunctioning traffic light, typically flashing yellow repeatedly.
 *
 */
enum class RegularTrafficLightState {
    GREEN, RED_YELLOW, YELLOW, RED, BROKEN
}
