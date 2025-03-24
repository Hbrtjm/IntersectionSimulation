package com.example.traffic.utils

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class RoadTest {
    @Test
    fun getTrafficLight() {
        val trafficLight = TrafficLight(listOf(TurnType.FORWARD, TurnType.LEFT))
        val road = Road(trafficLight, listOf(TurnType.FORWARD, TurnType.LEFT))
        assertEquals(trafficLight, road.trafficLight)
    }

    @Test
    fun getTurnTypes() {
        val turnTypes = listOf(TurnType.FORWARD, TurnType.RIGHT)
        val road = Road(TrafficLight(turnTypes), turnTypes)
        assertEquals(turnTypes, road.turnTypes)
    }

    @Test
    fun add() {
        val road = Road(TrafficLight(listOf(TurnType.FORWARD)), listOf(TurnType.FORWARD))
        val car = Car("CAR123", Direction.NORTH, Direction.SOUTH)
        road.add(car)
        assertEquals(1, road.vehicleCount())
    }

    @Test
    fun vehicleCount() {
        val road = Road(TrafficLight(listOf(TurnType.FORWARD)), listOf(TurnType.FORWARD))
        assertEquals(0, road.vehicleCount())
        road.add(Car("CAR123", Direction.NORTH, Direction.SOUTH))
        road.add(Car("CAR456", Direction.EAST, Direction.WEST))
        assertEquals(2, road.vehicleCount())
    }

    @Test
    fun moveCars() {
        val trafficLight = TrafficLight(listOf(TurnType.FORWARD))
        val road = Road(trafficLight, listOf(TurnType.FORWARD))
        val car = Car("CAR123", Direction.NORTH, Direction.SOUTH)
        road.add(car)

        trafficLight.setTrafficLightState(TrafficLightState.RED)
        assertNull(road.moveCars(Direction.NORTH, Direction.SOUTH))

        trafficLight.setTrafficLightState(TrafficLightState.GREEN)
        assertEquals(car, road.moveCars(Direction.NORTH, Direction.SOUTH))
        assertEquals(0, road.vehicleCount())
    }

    @Test
    fun testToString() {
        val road = Road(TrafficLight(listOf(TurnType.FORWARD)), listOf(TurnType.FORWARD))
        val expectedString = "Road(cars=${road.cars}, trafficLight=${road.trafficLight}, turnTypes=${road.turnTypes})"
        assertEquals(expectedString, road.toString())
    }
}