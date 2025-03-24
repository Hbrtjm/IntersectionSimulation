package com.example.traffic.utils

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class DirectionTest {

    @Test
    fun mapDirection() {
        assertEquals(0, mapDirection(Direction.NORTH))
        assertEquals(1, mapDirection(Direction.EAST))
        assertEquals(2, mapDirection(Direction.SOUTH))
        assertEquals(3, mapDirection(Direction.WEST))
    }
}
