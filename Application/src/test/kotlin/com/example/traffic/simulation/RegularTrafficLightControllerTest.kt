package com.example.traffic.simulation

import com.example.traffic.utils.*
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach

class RegularTrafficLightControllerTest {

    private lateinit var roads: MutableMap<Pair<Direction, Direction>, MutableList<Road>>
    private lateinit var regularTrafficLightController: RegularTrafficLightController
    private lateinit var lights: MutableMap<Pair<Direction, Direction>, MutableList<RegularTrafficLight>>
    @BeforeEach
    fun setUp() {
        roads = mutableMapOf()
        lights = mutableMapOf()
        // Initialize roads for different direction pairs
        for (start in Direction.entries) 
        {
            for (end in Direction.entries) // Omnidirectional roads
            {
                if (start != end) 
                {
                    val road = Road(
                        listOf(TurnType.FORWARD, TurnType.LEFT, TurnType.RIGHT)
                    )
                    roads[Pair(start, end)] = mutableListOf(road)
                }
            }
        }

        regularTrafficLightController = RegularTrafficLightController(roads, lights)
    }

    @Test
    fun init() {
        assertTrue(regularTrafficLightController.activeRoads.isEmpty(), "Initially, no roads should be active")
    }

    @Test
    fun stepActivate() {
        val busiestPair = Pair(Direction.NORTH, Direction.SOUTH)
        val road = roads[busiestPair]?.firstOrNull() ?: fail("Road should exist")

        repeat(5) {
            road.add(Car("CAR$it", Direction.NORTH, Direction.SOUTH))
        }

        regularTrafficLightController.step()

        assertTrue(regularTrafficLightController.activeRoads.contains(busiestPair), "Busiest road should be active")
        lights[busiestPair]?.get(0)?.let { assertTrue(it.grantsPassage(), "Traffic light on busiest road should be green") }

        for ((pair, roadList) in roads)
        {
            if (pair != busiestPair)
            {
                for (r in roadList)
                {
                    lights[busiestPair]?.get(0)?.let { assertFalse(it.grantsPassage(), "Traffic light on busiest road should be green") }
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

        regularTrafficLightController.step()

        val activeRoads = regularTrafficLightController.activeRoads
        assertEquals(1, activeRoads.size, "Busiest roads should be active and should not collide")
        assertEquals(busiestPair, activeRoads.first(), "The busiest road should be active")
    }
}
