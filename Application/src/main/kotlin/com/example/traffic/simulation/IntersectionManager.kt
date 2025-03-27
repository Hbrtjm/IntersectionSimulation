package com.example.traffic.simulation

import com.example.traffic.utils.*
import com.example.traffic.utils.TurnTypeFunctions.getDirection

/**
 *
 * TODO - Still have to refactor this
 *
 */
class IntersectionManager {
    private val roads: MutableMap<Pair<Direction,Direction>, MutableList<Road>> = mutableMapOf()
    private val trafficLightsDirections: MutableMap<Pair<Direction,Direction>, MutableList<RegularTrafficLight>> = mutableMapOf()
    private val vehicleQueue = VehicleQueueHandler(roads)
    private val regularTrafficLightController = RegularTrafficLightController(roads,trafficLightsDirections)
    private val cartesianProductOfRoads = Direction.entries.flatMap { firstElement ->
        Direction.entries.map { secondElement -> Pair(firstElement, secondElement) }
    }
    init {
        for (direction in cartesianProductOfRoads) {
            roads[direction] = listOf(
                Road(
                    listOf(TurnType.FORWARD, TurnType.LEFT, TurnType.RIGHT)
                )
            ).toMutableList()
            val newRegularTrafficLight = RegularTrafficLight(listOf(TurnType.FORWARD, TurnType.LEFT, TurnType.RIGHT))
            roads[direction]?.toList()?.let { newRegularTrafficLight.registerMultipleRoads(it) }
            trafficLightsDirections[direction] = listOf(newRegularTrafficLight).toMutableList()
        }
    }

    fun simulationStep(): List<String> {
        val directions = regularTrafficLightController.step()
        // TODO - FIX
        return vehicleQueue.step(directions)
    }

    fun addVehicle(vehicleId: String, start: Direction, end: Direction) {
        vehicleQueue.addVehicle(Car(vehicleId, start, end))
    }

    fun addRoad(direction: Direction, turnTypes: List<TurnType>?)
    {
        // There should be at least one road!!!
        turnTypes?.forEach { turnType ->
            roads[Pair(direction, getDirection(turnType,direction))]?.add(Road(
                listOf(TurnType.FORWARD, TurnType.LEFT, TurnType.RIGHT)
            ))
        }
    }
}