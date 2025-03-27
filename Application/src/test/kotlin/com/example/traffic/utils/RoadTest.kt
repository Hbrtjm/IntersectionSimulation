package com.example.traffic.utils

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class RoadTest {
    // TODO - Delete this, refactor
//    @Test
//    fun getTrafficLightTest() {
//        val regularTrafficLight = RegularTrafficLight(listOf(TurnType.FORWARD, TurnType.LEFT))
//        val road = Road(listOf(TurnType.FORWARD, TurnType.LEFT))
//        assertEquals(regularTrafficLight, road.subscribedTrafficLights[0])
//    }

    @Test
    fun getTurnTypesTest() {
        val turnTypes = listOf(TurnType.FORWARD, TurnType.RIGHT)
        val road = Road(turnTypes)
        assertEquals(turnTypes, road.turnTypes)
    }

    @Test
    fun addTest() {
        val road = Road(listOf(TurnType.FORWARD))
        val car = Car("vehicle1", Direction.NORTH, Direction.SOUTH)
        road.add(car)
        assertEquals(1, road.vehicleCount())
    }

    @Test
    fun vehicleCountTest() {
        val road = Road(listOf(TurnType.FORWARD))
        assertEquals(0, road.vehicleCount())
        road.add(Car("vehicle1", Direction.NORTH, Direction.SOUTH))
        road.add(Car("vehicle2", Direction.EAST, Direction.WEST))
        assertEquals(2, road.vehicleCount())
    }

    @Test
    fun moveCarsTest() {
        val regularTrafficLight = RegularTrafficLight(listOf(TurnType.FORWARD))
        val road = Road(listOf(TurnType.FORWARD))
        val car = Car("vehicle1", Direction.NORTH, Direction.SOUTH)
        road.add(car)
        regularTrafficLight.registerRoad(road)

        regularTrafficLight.setState(RegularTrafficLightState.RED)
        assertNull(road.moveCars(Direction.NORTH, Direction.SOUTH))

        regularTrafficLight.setState(RegularTrafficLightState.GREEN)
        assertEquals(car, road.moveCars(Direction.NORTH, Direction.SOUTH))
        assertEquals(0, road.vehicleCount())
    }

    @Test
    fun testToStringTest() {
        val road = Road(listOf(TurnType.FORWARD))
        val expectedString = "Road(cars=${road.cars}, turnTypes=${road.turnTypes})"
        assertEquals(expectedString, road.toString())
    }
}