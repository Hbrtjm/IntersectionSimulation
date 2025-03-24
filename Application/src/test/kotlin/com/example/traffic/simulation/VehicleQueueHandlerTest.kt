package com.example.traffic.simulation

import com.example.traffic.utils.*
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows

class VehicleQueueHandlerTest {

    private lateinit var roads: MutableMap<Pair<Direction, Direction>, MutableList<Road>>
    private lateinit var vehicleQueueHandler: VehicleQueueHandler

    @BeforeEach
    fun setUp() {
        roads = mutableMapOf()

        for (start in Direction.entries)
        {
            for (end in Direction.entries) // Omnidirectional crossing
            { 
                if (start != end) 
                {
                    val road = Road(
                        TrafficLight(listOf(TurnType.FORWARD, TurnType.LEFT, TurnType.RIGHT)),
                        listOf(TurnType.FORWARD, TurnType.LEFT, TurnType.RIGHT)
                    )
                    roads[Pair(start, end)] = mutableListOf(road)
                }
            }
        }

        vehicleQueueHandler = VehicleQueueHandler(roads)
    }

    @Test
    fun addVehicle() {
        val car = Car("vehicle1", Direction.NORTH, Direction.SOUTH)

        vehicleQueueHandler.addVehicle(car)

        val roadList = roads[Pair(Direction.NORTH, Direction.SOUTH)]
        assertNotNull(roadList, "Road should exist")
    }

    @Test
    fun addVehicleUniqueId() {
        val car = Car("vehicle1", Direction.EAST, Direction.WEST)

        vehicleQueueHandler.addVehicle(car)

        val exception = assertThrows<IllegalStateException> {
            vehicleQueueHandler.addVehicle(car) // Adding the same car again should fail
        }

        assertEquals("Car already exists in the set", exception.message)
    }

    // TODO
    @Test
    fun activeRoadsTest() {
        val car1 = Car("CAR1", Direction.NORTH, Direction.SOUTH)
        val car2 = Car("CAR2", Direction.EAST, Direction.WEST)

        vehicleQueueHandler.addVehicle(car1)
        vehicleQueueHandler.addVehicle(car2)

        val activeRoads = listOf(Pair(Direction.NORTH, Direction.SOUTH))
        val leftVehicles = vehicleQueueHandler.step(activeRoads)
    }
}
