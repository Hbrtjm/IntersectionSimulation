package com.example.traffic.commands

import com.example.traffic.simulation.IntersectionManager
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class CommandTranslatorTest {

    private lateinit var intersectionManager: IntersectionManager
    private lateinit var stepResults: MutableList<StepResponse>

    @BeforeEach
    fun setUp() {
        intersectionManager = IntersectionManager()
        stepResults = mutableListOf()
    }

    @Test
    fun addVehicleTest() {
        CommandTranslator.translateCommand(
            type = "addVehicle",
            startRoad = "north",
            endRoad = "south",
            vehicleId = "vehicle1",
            direction = null,
            turnTypes = null,
            stepResults = stepResults,
            intersectionManager = intersectionManager
        )
        assertTrue(stepResults.isEmpty())
    }

    @Test
    fun addVehicle_MissingParametersTest() {
        CommandTranslator.translateCommand(
            type = "addVehicle",
            startRoad = null,
            endRoad = "south",
            vehicleId = "vehicle2",
            direction = null,
            turnTypes = null,
            stepResults = stepResults,
            intersectionManager = intersectionManager
        )
        if (DebugModeController.isDebugModeOn()) {
            assertEquals(1, stepResults.size)
            assertTrue(stepResults[0] is ListStepResponse)
            assertEquals("Missing parameters for addVehicle", (stepResults[0] as ListStepResponse).response[0])
        } else {
            assertTrue(stepResults.isEmpty())
        }
    }

    @Test
    fun testStepCommandTest() {
        CommandTranslator.translateCommand(
            type = "step",
            startRoad = null,
            endRoad = null,
            vehicleId = null,
            direction = null,
            turnTypes = null,
            stepResults = stepResults,
            intersectionManager = intersectionManager
        )
        assertEquals(1, stepResults.size)
        assertTrue(stepResults[0] is JsonStepResponse)
        val response = (stepResults[0] as JsonStepResponse).response
        assertTrue(response.containsKey("carsLeft"))
    }

    @Test
    fun addRoadTest() {
        CommandTranslator.translateCommand(
            type = "addRoad",
            startRoad = null,
            endRoad = null,
            vehicleId = null,
            direction = "east",
            turnTypes = listOf("LEFT", "RIGHT"),
            stepResults = stepResults,
            intersectionManager = intersectionManager
        )
        if (DebugModeController.isDebugModeOn()) {
            assertEquals(1, stepResults.size)
            assertTrue(stepResults[0] is ListStepResponse)
            assertEquals("Added Road: direction: east and turnTypes: [LEFT, RIGHT]", (stepResults[0] as ListStepResponse).response[0])
        } else {
            assertTrue(stepResults.isEmpty())
        }
    }

    @Test
    fun unknownCommandTest() {
        CommandTranslator.translateCommand(
            type = "invalidCommand",
            startRoad = null,
            endRoad = null,
            vehicleId = null,
            direction = null,
            turnTypes = null,
            stepResults = stepResults,
            intersectionManager = intersectionManager
        )
        assertEquals(1, stepResults.size)
        assertTrue(stepResults[0] is ListStepResponse)
        assertEquals("Unknown command type: invalidCommand", (stepResults[0] as ListStepResponse).response[0])
    }
}
