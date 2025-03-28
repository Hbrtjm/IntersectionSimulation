package com.example.traffic.simulation

import com.example.traffic.utils.Direction
import com.example.traffic.utils.TurnType
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach


class IntersectionControllerTest {

    private lateinit var intersectionController: IntersectionController

    @BeforeEach
    fun setUp() {
        intersectionController = IntersectionController()
    }

    @Test
    fun addVehicleTest() {
        val vehicleId = "vehicle1"
        val start = Direction.NORTH
        val end = Direction.SOUTH
        intersectionController.addVehicle(vehicleId, start, end)

        val leftVehicles = intersectionController.simulationStep()
        assertTrue(leftVehicles.contains(vehicleId), "Vehicle should have departed")
    }

    @Test
    fun addRoadTest() {
        val direction = Direction.WEST
        val turnTypes = listOf(TurnType.LEFT, TurnType.RIGHT)

        intersectionController.addRoad(direction, turnTypes)

        assertDoesNotThrow {
            intersectionController.addRoad(direction, turnTypes)
        }
    }

    @Test
    fun step() {
        val vehicle1 = "vehicle1"
        val vehicle2 = "vehicle2"
        val vehicle3 = "vehicle3"

        intersectionController.addVehicle(vehicle1, Direction.NORTH, Direction.SOUTH)
        intersectionController.addVehicle(vehicle2, Direction.EAST, Direction.WEST)
        intersectionController.addVehicle(vehicle3, Direction.WEST, Direction.EAST)

        val leftVehicles = intersectionController.simulationStep()

        // At least one vehicle should be processed
        assertTrue(leftVehicles.isNotEmpty(), "Simulation step should process at least one vehicle")
        assertTrue(leftVehicles.contains(vehicle1), "It should be the first vehicle that was added $leftVehicles")
    }
}
