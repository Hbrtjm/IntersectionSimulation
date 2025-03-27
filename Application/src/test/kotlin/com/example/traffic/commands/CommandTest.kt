package com.example.traffic.commands

import kotlinx.serialization.json.Json
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class CommandTest {

    private lateinit var commands: List<Command>

    @BeforeEach
    fun setUp()
    {
        // Test given in the mail
        val json = """
            {
              "commands": [
                { "type": "addVehicle", "vehicleId": "vehicle1", "startRoad": "south", "endRoad": "north" },
                { "type": "addVehicle", "vehicleId": "vehicle2", "startRoad": "north", "endRoad": "south" },
                { "type": "step" },
                { "type": "step" },
                { "type": "addVehicle", "vehicleId": "vehicle3", "startRoad": "west", "endRoad": "south" },
                { "type": "addVehicle", "vehicleId": "vehicle4", "startRoad": "west", "endRoad": "south" },
                { "type": "step" },
                { "type": "step" }
              ]
            }
        """

        val jsonObject = Json.decodeFromString<Map<String, List<Command>>>(json)
        commands = jsonObject["commands"] ?: emptyList()
    }

    @Test
    fun getTypeTest()
    {
        assertEquals("addVehicle", commands[0].type)
        assertEquals("step", commands[2].type)
    }

    @Test
    fun getVehicleIdTest() {
        assertEquals("vehicle1", commands[0].vehicleId)
        assertNull(commands[2].vehicleId)
    }

    @Test
    fun getStartRoadTest() {
        assertEquals("south", commands[0].startRoad)
        assertEquals("west", commands[4].startRoad)
        assertNull(commands[2].startRoad)
    }

    @Test
    fun getEndRoadTest() {
        assertEquals("north", commands[0].endRoad)
        assertEquals("south", commands[4].endRoad)
        assertNull(commands[2].endRoad)
    }

    @Test
    fun getDirectionTest() {
        assertNull(commands[0].direction)
    }

    @Test
    fun getTurnTypeTest() {
        assertNull(commands[0].turnTypes)
    }
}
