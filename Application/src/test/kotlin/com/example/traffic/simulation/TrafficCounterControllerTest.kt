package com.example.traffic.simulation

import com.example.traffic.utils.*
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach

class TrafficCounterControllerTest {

    private lateinit var roads: MutableMap<Pair<Direction, Direction>, MutableList<Road>>
    private lateinit var trafficCounterController: TrafficCounterController

    @BeforeEach
    fun setUp() {
        roads = mutableMapOf()

        for (start in Direction.entries) 
        {
            for (end in Direction.entries) 
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

        trafficCounterController = TrafficCounterController(roads)
    }

    @Test
    fun getBusiestDirectionEmptyTest() {
        val busiestDirections = trafficCounterController.getBusiestDirections()
        assertTrue(busiestDirections.isEmpty(), "No roads should be marked as busiest when there are no vehicles $busiestDirections")
    }

    @Test
    fun getBusiestDirectionTest() {
        val busiestPair = Pair(Direction.NORTH, Direction.SOUTH)
        val road = roads[busiestPair]?.firstOrNull() ?: fail("Road should exist")

        repeat(5) { road.add(Car("CAR$it", Direction.NORTH, Direction.SOUTH)) }

        val busiestDirections = trafficCounterController.getBusiestDirections()
        assertEquals(1, busiestDirections.size, "Only one direction should be marked as busiest")
        assertEquals(busiestPair, busiestDirections.first(), "The correct road should be marked as busiest")
    }

    @Test
    fun getBusiestDirectionEqualTest() {
        val road1Pair = Pair(Direction.EAST, Direction.WEST)
        val road2Pair = Pair(Direction.WEST, Direction.EAST)
        val road3Pair = Pair(Direction.NORTH, Direction.WEST) // Any road that has different starting position than East or West
        val road1 = roads[road1Pair]?.firstOrNull() ?: fail("Road should exist")
        val road2 = roads[road2Pair]?.firstOrNull() ?: fail("Road should exist")
        val road3 = roads[road3Pair]?.firstOrNull() ?: fail("Road should exist")

        road3.add(Car("NORTH_WEST_PASSAGE", Direction.NORTH, Direction.WEST))
        repeat(3) {
            road1.add(Car("EAST_WEST_$it", Direction.EAST, Direction.WEST))
            road2.add(Car("WEST_EAST_$it", Direction.WEST, Direction.EAST))
        }

        val busiestDirections = trafficCounterController.getBusiestDirections()
        assertEquals(2, busiestDirections.size, "Both roads should be marked as busiest $busiestDirections")
        assertTrue(busiestDirections.contains(road1Pair), "East-to-West road should be marked busiest")
        assertTrue(busiestDirections.contains(road2Pair), "West-to-East road should be marked busiest")
    }


    @Test
    fun checkCollisionTest() {
        assertFalse(trafficCounterController.checkCollision(Direction.NORTH, Direction.SOUTH, Direction.NORTH, Direction.SOUTH), "Same path should not collide")
        assertFalse(trafficCounterController.checkCollision(Direction.WEST, Direction.EAST, Direction.WEST, Direction.EAST), "Same path should not collide")
        assertFalse(trafficCounterController.checkCollision(Direction.NORTH, Direction.SOUTH, Direction.SOUTH, Direction.NORTH), "Opposite directions should not collide")
        assertFalse(trafficCounterController.checkCollision(Direction.WEST, Direction.EAST, Direction.EAST, Direction.WEST), "Opposite directions should not collide")

        assertTrue(trafficCounterController.checkCollision(Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST), "Crossing roads should collide")
        assertTrue(trafficCounterController.checkCollision(Direction.NORTH, Direction.WEST, Direction.SOUTH, Direction.EAST), "Angled turns should collide")
        assertTrue(trafficCounterController.checkCollision(Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST), "Parallel movements should not collide")
        assertTrue(trafficCounterController.checkCollision(Direction.EAST, Direction.SOUTH, Direction.WEST, Direction.NORTH), "Parallel movements should not collide")

        assertFalse(trafficCounterController.checkCollision(Direction.WEST, Direction.SOUTH, Direction.EAST, Direction.NORTH), "Parallel movements should not collide")
    }

}