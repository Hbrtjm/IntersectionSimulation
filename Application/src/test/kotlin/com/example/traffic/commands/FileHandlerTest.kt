package com.example.traffic.commands

import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import java.io.File

class FileHandlerTest {

    private val filename: String = "test.json"

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
        val testFile = File(filename)
        testFile.createNewFile()
        testFile.writeText(json)
    }

    @Test
    fun readDataTest() {
        FileHandler(filename)
    }
}