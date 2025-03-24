package com.example.traffic.simulation

import com.example.traffic.utils.Direction
import com.example.traffic.utils.TurnType
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach


class IntersectionManagerTest {

    private lateinit var intersectionManager: IntersectionManager

    @BeforeEach
    fun setUp() {
        intersectionManager = IntersectionManager()
    }

    @Test
    fun addVehicle() {
        val vehicleId = "vehicle1"
        val start = Direction.NORTH
        val end = Direction.SOUTH
        intersectionManager.addVehicle(vehicleId, start, end)

        val leftVehicles = intersectionManager.simulationStep()
        assertTrue(leftVehicles.contains(vehicleId), "Vehicle should have departed")
    }

    @Test
    fun addRoad() {
        val direction = Direction.WEST
        val turnTypes = listOf(TurnType.LEFT, TurnType.RIGHT)

        intersectionManager.addRoad(direction, turnTypes)

        assertDoesNotThrow {
            intersectionManager.addRoad(direction, turnTypes)
        }
    }

    @Test
    fun step() {
        val vehicle1 = "vehicle1"
        val vehicle2 = "vehicle2"
        val vehicle3 = "vehicle3"

        intersectionManager.addVehicle(vehicle1, Direction.NORTH, Direction.SOUTH)
        intersectionManager.addVehicle(vehicle2, Direction.EAST, Direction.WEST)
        intersectionManager.addVehicle(vehicle3, Direction.WEST, Direction.EAST)

        val leftVehicles = intersectionManager.simulationStep()

        // At least one vehicle should be processed
        assertTrue(leftVehicles.isNotEmpty(), "Simulation step should process at least one vehicle")
        assertTrue(leftVehicles.contains(vehicle1), "It should be the first vehicle that was added")
    }
}
