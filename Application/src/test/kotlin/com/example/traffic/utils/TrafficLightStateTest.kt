package com.example.traffic.utils

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class RegularRegularTrafficLightStateEnumTest {

    @Test
    fun getEntriesTest() {
        val entries = RegularTrafficLightState.entries
        assertTrue(entries.containsAll(listOf(RegularTrafficLightState.RED,RegularTrafficLightState.GREEN,RegularTrafficLightState.YELLOW)))
    }

    @Test
    fun valuesTest() {
        val values = RegularTrafficLightState.entries.toTypedArray()
        assertEquals(5, values.size)
        assertEquals(RegularTrafficLightState.GREEN, values[0])
        assertEquals(RegularTrafficLightState.RED_YELLOW, values[1])
        assertEquals(RegularTrafficLightState.YELLOW, values[2])
        assertEquals(RegularTrafficLightState.RED, values[3])
        assertEquals(RegularTrafficLightState.BROKEN, values[4])
    }

    @Test
    fun valueOfTest() {
        assertEquals(RegularTrafficLightState.GREEN, RegularTrafficLightState.valueOf("GREEN"))
        assertEquals(RegularTrafficLightState.YELLOW, RegularTrafficLightState.valueOf("YELLOW"))
        assertEquals(RegularTrafficLightState.RED, RegularTrafficLightState.valueOf("RED"))
    }
}