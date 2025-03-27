package com.example.traffic.utils

import com.example.traffic.utils.TurnTypeFunctions.getDirection
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class RegularTrafficLightTest {

    @Test
    fun setTrafficLightStateTest() {
        val regularTrafficLight = RegularTrafficLight(listOf(TurnType.FORWARD))
        regularTrafficLight.setState(RegularTrafficLightState.GREEN)
        assertTrue(regularTrafficLight.grantsPassage())

        regularTrafficLight.setState(RegularTrafficLightState.RED)
        assertFalse(regularTrafficLight.grantsPassage())
    }

    @Test
    fun isGreenTest() {
        val regularTrafficLight = RegularTrafficLight(listOf(TurnType.FORWARD))
        assertFalse(regularTrafficLight.grantsPassage()) // Default should be RED

        regularTrafficLight.setState(RegularTrafficLightState.GREEN)
        assertTrue(regularTrafficLight.grantsPassage())
    }

    @Test
    fun testEqualsTest() {
        val regularTrafficLight1 = RegularTrafficLight(listOf(TurnType.FORWARD, TurnType.LEFT))
        val regularTrafficLight2 = RegularTrafficLight(listOf(TurnType.FORWARD, TurnType.LEFT))
        val regularTrafficLight3 = RegularTrafficLight(listOf(TurnType.RIGHT))

        assertEquals(regularTrafficLight1, regularTrafficLight2)
        assertNotEquals(regularTrafficLight1, regularTrafficLight3)
    }

    @Test
    fun testHashCodeTest() {
        val regularTrafficLight1 = RegularTrafficLight(listOf(TurnType.FORWARD, TurnType.LEFT))
        val regularTrafficLight2 = RegularTrafficLight(listOf(TurnType.FORWARD, TurnType.LEFT))
        val regularTrafficLight3 = RegularTrafficLight(listOf(TurnType.RIGHT))

        assertEquals(regularTrafficLight1.hashCode(), regularTrafficLight2.hashCode())
        assertNotEquals(regularTrafficLight1.hashCode(), regularTrafficLight3.hashCode())
    }
}
