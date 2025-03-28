package com.example.traffic.utils

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach

class CarTest {
    private lateinit var car: Car

    @BeforeEach
    fun setUp()
    {
        car = Car("vehicle1", Direction.WEST, Direction.EAST)
    }

    @Test
    fun toStringTest() {
        val car = Car("vehicle1", Direction.NORTH, Direction.SOUTH)
        val expectedString = "Car(carID=\'vehicle1\', startPoint=NORTH, exitPoint=SOUTH)"
        assertEquals(expectedString, car.toString())
    }

    @Test
    fun equalsTest() {
        val car1 = Car("vehicle1", Direction.NORTH, Direction.SOUTH)
        val car2 = Car("vehicle1", Direction.NORTH, Direction.SOUTH)
        val car3 = Car("vehicle3", Direction.EAST, Direction.WEST)

        assertEquals(car1, car2)
        assertNotEquals(car1, car3)
    }

    @Test
    fun getCarIDTest() {
        assertEquals("vehicle1", car.carID)
    }

    @Test
    fun getStartPointTest() {
        assertEquals(Direction.WEST, car.startPoint)
    }

    @Test
    fun getExitPointTest() {
        assertEquals(Direction.EAST, car.exitPoint)
    }
}