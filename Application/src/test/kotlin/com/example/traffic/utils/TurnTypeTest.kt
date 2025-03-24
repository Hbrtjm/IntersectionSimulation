package com.example.traffic.utils

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*


class TurnTypeFunctionsTest {

    @Test
    fun getDirectionForward() {
        assertEquals(Direction.SOUTH, getDirectionForward(Direction.NORTH))
        assertEquals(Direction.WEST, getDirectionForward(Direction.EAST))
        assertEquals(Direction.NORTH, getDirectionForward(Direction.SOUTH))
        assertEquals(Direction.EAST, getDirectionForward(Direction.WEST))
    }

    @Test
    fun getDirectionLeft() {
        assertEquals(Direction.EAST, getDirectionLeft(Direction.NORTH))
        assertEquals(Direction.SOUTH, getDirectionLeft(Direction.EAST))
        assertEquals(Direction.WEST, getDirectionLeft(Direction.SOUTH))
        assertEquals(Direction.NORTH, getDirectionLeft(Direction.WEST))
    }

    @Test
    fun getDirectionRight() {
        assertEquals(Direction.WEST, getDirectionRight(Direction.NORTH))
        assertEquals(Direction.NORTH, getDirectionRight(Direction.EAST))
        assertEquals(Direction.EAST, getDirectionRight(Direction.SOUTH))
        assertEquals(Direction.SOUTH, getDirectionRight(Direction.WEST))
    }

    @Test
    fun getDirectionTurnaround() {
        assertEquals(Direction.NORTH, getDirectionTurnaround(Direction.NORTH))
        assertEquals(Direction.EAST, getDirectionTurnaround(Direction.EAST))
        assertEquals(Direction.SOUTH, getDirectionTurnaround(Direction.SOUTH))
        assertEquals(Direction.WEST, getDirectionTurnaround(Direction.WEST))
    }

    @Test
    fun getDirection() {
        assertEquals(Direction.SOUTH, getDirection(TurnType.FORWARD, Direction.NORTH))
        assertEquals(Direction.EAST, getDirection(TurnType.LEFT, Direction.NORTH))
        assertEquals(Direction.WEST, getDirection(TurnType.RIGHT, Direction.NORTH))
        assertEquals(Direction.NORTH, getDirection(TurnType.TURN_AROUND, Direction.NORTH))
    }

    @Test
    fun getTurnType() {
        // Only NORTH
        assertEquals(TurnType.FORWARD, getTurnType(Direction.NORTH, Direction.SOUTH))
        assertEquals(TurnType.LEFT, getTurnType(Direction.NORTH, Direction.EAST))
        assertEquals(TurnType.RIGHT, getTurnType(Direction.NORTH, Direction.WEST))
        assertEquals(TurnType.TURN_AROUND, getTurnType(Direction.NORTH, Direction.NORTH))

        // Only WEST
        assertEquals(TurnType.FORWARD, getTurnType(Direction.WEST, Direction.EAST))
        assertEquals(TurnType.LEFT, getTurnType(Direction.WEST, Direction.NORTH))
        assertEquals(TurnType.RIGHT, getTurnType(Direction.WEST, Direction.SOUTH))
        assertEquals(TurnType.TURN_AROUND, getTurnType(Direction.WEST, Direction.WEST))

        // Only SOUTH
        assertEquals(TurnType.FORWARD, getTurnType(Direction.SOUTH, Direction.NORTH))
        assertEquals(TurnType.LEFT, getTurnType(Direction.SOUTH, Direction.WEST))
        assertEquals(TurnType.RIGHT, getTurnType(Direction.SOUTH, Direction.EAST))
        assertEquals(TurnType.TURN_AROUND, getTurnType(Direction.SOUTH, Direction.SOUTH))

        // Only EAST
        assertEquals(TurnType.FORWARD, getTurnType(Direction.EAST, Direction.WEST))
        assertEquals(TurnType.LEFT, getTurnType(Direction.EAST, Direction.SOUTH))
        assertEquals(TurnType.RIGHT, getTurnType(Direction.EAST, Direction.NORTH))
        assertEquals(TurnType.TURN_AROUND, getTurnType(Direction.EAST, Direction.EAST))
    }
}

class TurnTypeEnumTest {

    @Test
    fun getEntries() {
        val entries = TurnType.entries
        assertTrue(entries.containsAll(listOf(TurnType.FORWARD, TurnType.LEFT, TurnType.RIGHT, TurnType.TURN_AROUND)))
    }

    @Test
    fun values() {
        val values = TurnType.values()
        assertEquals(4, values.size)
        assertEquals(TurnType.FORWARD, values[0])
        assertEquals(TurnType.LEFT, values[1])
        assertEquals(TurnType.RIGHT, values[2])
        assertEquals(TurnType.TURN_AROUND, values[3])
    }

    @Test
    fun valueOf() {
        assertEquals(TurnType.FORWARD, TurnType.valueOf("FORWARD"))
        assertEquals(TurnType.LEFT, TurnType.valueOf("LEFT"))
        assertEquals(TurnType.RIGHT, TurnType.valueOf("RIGHT"))
        assertEquals(TurnType.TURN_AROUND, TurnType.valueOf("TURN_AROUND"))
    }
}
