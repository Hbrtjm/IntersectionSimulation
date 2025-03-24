package com.example.traffic.utils

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import com.example.traffic.utils.*

class TrafficLightStateEnumTest {

    @Test
    fun getEntries() {
        val entries = TrafficLightState.entries
        assertTrue(entries.containsAll(listOf(TrafficLightState.RED,TrafficLightState.GREEN,TrafficLightState.YELLOW)))
    }

    @Test
    fun values() {
        val values = TrafficLightState.values()
        assertEquals(3, values.size)
        assertEquals(TrafficLightState.GREEN, values[0])
        assertEquals(TrafficLightState.YELLOW, values[1])
        assertEquals(TrafficLightState.RED, values[2])
    }

    @Test
    fun valueOf() {
        assertEquals(TrafficLightState.GREEN, TrafficLightState.valueOf("GREEN"))
        assertEquals(TrafficLightState.YELLOW, TrafficLightState.valueOf("YELLOW"))
        assertEquals(TrafficLightState.RED, TrafficLightState.valueOf("RED"))
    }
}

class TrafficLightStateFunctionsTest {

    @Test
    fun parseTrafficLight() {
        assertEquals(parseTrafficLight("ReD"),TrafficLightState.RED)
        assertEquals(parseTrafficLight("GREEN"),TrafficLightState.GREEN)
        assertEquals(parseTrafficLight("yellow"),TrafficLightState.YELLOW)
    }
}