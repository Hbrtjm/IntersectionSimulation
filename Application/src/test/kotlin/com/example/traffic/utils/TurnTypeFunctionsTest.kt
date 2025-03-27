package com.example.traffic.utils

import com.example.traffic.utils.TurnTypeFunctions.getDirection
import com.example.traffic.utils.TurnTypeFunctions.getDirectionForward
import com.example.traffic.utils.TurnTypeFunctions.getDirectionLeft
import com.example.traffic.utils.TurnTypeFunctions.getDirectionRight
import com.example.traffic.utils.TurnTypeFunctions.getDirectionTurnaround
import com.example.traffic.utils.TurnTypeFunctions.getTurnType
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*


class TurnTypeFunctionsTest {

    @Test
    fun getDirectionForwardTest() {
        assertEquals(Direction.SOUTH, getDirectionForward(Direction.NORTH))
        assertEquals(Direction.WEST, getDirectionForward(Direction.EAST))
        assertEquals(Direction.NORTH, getDirectionForward(Direction.SOUTH))
        assertEquals(Direction.EAST, getDirectionForward(Direction.WEST))
    }

    @Test
    fun getDirectionLeftTest() {
        assertEquals(Direction.EAST, getDirectionLeft(Direction.NORTH))
        assertEquals(Direction.SOUTH, getDirectionLeft(Direction.EAST))
        assertEquals(Direction.WEST, getDirectionLeft(Direction.SOUTH))
        assertEquals(Direction.NORTH, getDirectionLeft(Direction.WEST))
    }

    @Test
    fun getDirectionRightTest() {
        assertEquals(Direction.WEST, getDirectionRight(Direction.NORTH))
        assertEquals(Direction.NORTH, getDirectionRight(Direction.EAST))
        assertEquals(Direction.EAST, getDirectionRight(Direction.SOUTH))
        assertEquals(Direction.SOUTH, getDirectionRight(Direction.WEST))
    }

    @Test
    fun getDirectionTurnaroundTest() {
        assertEquals(Direction.NORTH, getDirectionTurnaround(Direction.NORTH))
        assertEquals(Direction.EAST, getDirectionTurnaround(Direction.EAST))
        assertEquals(Direction.SOUTH, getDirectionTurnaround(Direction.SOUTH))
        assertEquals(Direction.WEST, getDirectionTurnaround(Direction.WEST))
    }

    @Test
    fun getDirectionTest() {
        assertEquals(Direction.SOUTH, getDirection(TurnType.FORWARD, Direction.NORTH))
        assertEquals(Direction.EAST, getDirection(TurnType.LEFT, Direction.NORTH))
        assertEquals(Direction.WEST, getDirection(TurnType.RIGHT, Direction.NORTH))
        assertEquals(Direction.NORTH, getDirection(TurnType.TURN_AROUND, Direction.NORTH))
    }

    @Test
    fun getTurnTypeTest() {
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
