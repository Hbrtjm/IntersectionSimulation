package com.example.traffic.simulation

import com.example.traffic.utils.*
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach

class TrafficLightControllerTest {

    private lateinit var roads: MutableMap<Pair<Direction, Direction>, MutableList<Road>>
    private lateinit var trafficLightController: TrafficLightController

    @BeforeEach
    fun setUp() {
        roads = mutableMapOf()

        // Initialize roads for different direction pairs
        for (start in Direction.entries) 
        {
            for (end in Direction.entries) // Omnidirectional roads
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

        trafficLightController = TrafficLightController(roads)
    }

    @Test
    fun init() {
        assertTrue(trafficLightController.activeRoads.isEmpty(), "Initially, no roads should be active")
    }

    @Test
    fun stepActivate() {
        val busiestPair = Pair(Direction.NORTH, Direction.SOUTH)
        val road = roads[busiestPair]?.firstOrNull() ?: fail("Road should exist")

        repeat(5) { 
            road.add(Car("CAR$it", Direction.NORTH, Direction.SOUTH)) 
        }

        trafficLightController.step()

        assertTrue(trafficLightController.activeRoads.contains(busiestPair), "Busiest road should be active")
        assertEquals(TrafficLightState.GREEN, road.trafficLight.trafficLightCurrentState, "Traffic light on busiest road should be green")

        for ((pair, roadList) in roads) 
        {
            if (pair != busiestPair) 
            {
                for (r in roadList) 
                {
                    assertEquals(TrafficLightState.RED, r.trafficLight.trafficLightCurrentState, "Non-busiest roads should have red lights")
                }
            }
        }
    }

    @Test
    fun stepBusiest() {
        val busiestPair = Pair(Direction.EAST, Direction.WEST)
        val anotherPair = Pair(Direction.NORTH, Direction.SOUTH)
        val road1 = roads[busiestPair]?.firstOrNull() ?: fail("Road should exist")
        val road2 = roads[anotherPair]?.firstOrNull() ?: fail("Road should exist")

        road2.add(Car("dummy", Direction.NORTH, Direction.SOUTH))
        repeat(3) 
        {
            road1.add(Car("EAST_WEST_$it", Direction.EAST, Direction.WEST))
        }

        trafficLightController.step()

        val activeRoads = trafficLightController.activeRoads
        assertEquals(1, activeRoads.size, "Busiest roads should be active and should not collide")
        assertEquals(busiestPair, activeRoads.first(), "The busiest road should be active")
    }
}
