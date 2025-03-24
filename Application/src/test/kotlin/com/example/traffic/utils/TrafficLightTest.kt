package com.example.traffic.utils

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class TrafficLightTest {

    @Test
    fun getTrafficLightDirection() {
        val trafficLight = TrafficLight(listOf(TurnType.FORWARD, TurnType.LEFT))
        val directions = trafficLight.getTrafficLightDirection(Direction.NORTH)

        assertTrue(directions.contains(getDirection(TurnType.FORWARD, Direction.NORTH)))
        assertTrue(directions.contains(getDirection(TurnType.LEFT, Direction.NORTH)))
    }

    @Test
    fun setTrafficLightState() {
        val trafficLight = TrafficLight(listOf(TurnType.FORWARD))
        trafficLight.setTrafficLightState(TrafficLightState.GREEN)
        assertTrue(trafficLight.isGreen())

        trafficLight.setTrafficLightState(TrafficLightState.RED)
        assertFalse(trafficLight.isGreen())
    }

    @Test
    fun isGreen() {
        val trafficLight = TrafficLight(listOf(TurnType.FORWARD))
        assertFalse(trafficLight.isGreen()) // Default should be RED

        trafficLight.setTrafficLightState(TrafficLightState.GREEN)
        assertTrue(trafficLight.isGreen())
    }

    @Test
    fun testEquals() {
        val trafficLight1 = TrafficLight(listOf(TurnType.FORWARD, TurnType.LEFT))
        val trafficLight2 = TrafficLight(listOf(TurnType.FORWARD, TurnType.LEFT))
        val trafficLight3 = TrafficLight(listOf(TurnType.RIGHT))

        assertEquals(trafficLight1, trafficLight2)
        assertNotEquals(trafficLight1, trafficLight3)
    }

    @Test
    fun testHashCode() {
        val trafficLight1 = TrafficLight(listOf(TurnType.FORWARD, TurnType.LEFT))
        val trafficLight2 = TrafficLight(listOf(TurnType.FORWARD, TurnType.LEFT))
        val trafficLight3 = TrafficLight(listOf(TurnType.RIGHT))

        assertEquals(trafficLight1.hashCode(), trafficLight2.hashCode())
        assertNotEquals(trafficLight1.hashCode(), trafficLight3.hashCode())
    }
}
