package com.example.traffic.utils

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class RoadBalancerTest {

    @Test
    fun addCarToRoads() {
        val roadBalancer = RoadBalancer()
        val car = Car("Vehicle1", Direction.NORTH, Direction.SOUTH)

        val road1 = Road(TrafficLight(listOf(TurnType.FORWARD)), listOf(TurnType.FORWARD)) // Valid for this car
        val road2 = Road(TrafficLight(listOf(TurnType.FORWARD, TurnType.LEFT)), listOf(TurnType.FORWARD, TurnType.LEFT)) // Also valid
        val road3 = Road(TrafficLight(listOf(TurnType.LEFT)), listOf(TurnType.LEFT)) // Not valid for this car

        val roads = mutableListOf(road1, road2, road3)
        roadBalancer.addCar(car, roads)
        assertTrue(road1.cars.contains(car) || road2.cars.contains(car), "Car should be assigned to a road that allows FORWARD movement")
        assertFalse(road3.cars.contains(car), "Car should NOT be assigned to a road that only allows LEFT movement")
    }

    @Test
    fun `test addCar - chooses road with least vehicles`() {
        val roadBalancer = RoadBalancer()
        val car1 = Car("Vehicle1", Direction.EAST, Direction.WEST)
        val car2 = Car("Vehicle2", Direction.EAST, Direction.WEST)
        val road1 = Road(TrafficLight(listOf(TurnType.FORWARD)), listOf(TurnType.FORWARD))
        val road2 = Road(TrafficLight(listOf(TurnType.FORWARD)), listOf(TurnType.FORWARD))

        road1.add(car1)
        val roads = mutableListOf(road1, road2)
        roadBalancer.addCar(car2, roads)

        assertTrue(road2.cars.contains(car2), "Car should be assigned to the less crowded road")
        assertFalse(road1.cars.contains(car2), "Car should NOT be assigned to the more crowded road")
    }

    @Test
    fun addCarNull() {
        val roadBalancer = RoadBalancer()
        val car = Car("vehicle1", Direction.NORTH, Direction.SOUTH)
        // In this case car is lost (!)
        assertDoesNotThrow { roadBalancer.addCar(car, null) }
    }
}