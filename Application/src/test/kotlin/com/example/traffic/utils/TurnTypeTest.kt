package com.example.traffic.utils

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class TurnTypeTest {

    @Test
    fun getEntriesTest() {
        val entries = TurnType.entries
        assertTrue(entries.containsAll(listOf(TurnType.FORWARD, TurnType.LEFT, TurnType.RIGHT, TurnType.TURN_AROUND)))
    }

    @Test
    fun valuesTest() {
        val values = TurnType.entries.toTypedArray()
        assertEquals(4, values.size)
        assertEquals(TurnType.FORWARD, values[0])
        assertEquals(TurnType.LEFT, values[1])
        assertEquals(TurnType.RIGHT, values[2])
        assertEquals(TurnType.TURN_AROUND, values[3])
    }

    @Test
    fun valueOfTest() {
        assertEquals(TurnType.FORWARD, TurnType.valueOf("FORWARD"))
        assertEquals(TurnType.LEFT, TurnType.valueOf("LEFT"))
        assertEquals(TurnType.RIGHT, TurnType.valueOf("RIGHT"))
        assertEquals(TurnType.TURN_AROUND, TurnType.valueOf("TURN_AROUND"))
    }
}
