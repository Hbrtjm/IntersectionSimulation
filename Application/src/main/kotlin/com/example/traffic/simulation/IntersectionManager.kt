package com.example.traffic.simulation

import com.example.traffic.utils.*

/**
 * This class handles the light cycles, I don't like the way I pass the roads to each Manager... I should also avoid manager classes... TODO
 */

class IntersectionManager {
    private val roads: MutableMap<Pair<Direction,Direction>, MutableList<Road>> = mutableMapOf()
    private val vehicleQueue = VehicleQueueHandler(roads)
    private val trafficLightController = TrafficLightController(roads)
    private val cartesianProductOfRoads = Direction.entries.flatMap { firstElement ->
        Direction.entries.map { secondElement -> Pair(firstElement, secondElement) }
    }
    init {
        for (direction in cartesianProductOfRoads) {
            roads[direction] = listOf(
                Road(
                    TrafficLight(listOf(TurnType.FORWARD, TurnType.LEFT, TurnType.RIGHT)),
                    listOf(TurnType.FORWARD, TurnType.LEFT, TurnType.RIGHT)
                )
            ).toMutableList()
        }
    }

    fun simulationStep(): List<String> {
        trafficLightController.step()
        return vehicleQueue.step(trafficLightController.activeRoads)
    }

    fun addVehicle(vehicleId: String, start: Direction, end: Direction) {
        vehicleQueue.addVehicle(Car(vehicleId, start, end))
    }

    fun addRoad(direction: Direction, turnTypes: List<TurnType>?)
    {
        // There should be at least one road!!!
        turnTypes?.forEach { turnType ->
            roads[Pair(direction, getDirection(turnType,direction))]?.add(Road(
                TrafficLight(listOf(TurnType.FORWARD, TurnType.LEFT, TurnType.RIGHT)),
                listOf(TurnType.FORWARD, TurnType.LEFT, TurnType.RIGHT)
            ))
        }
    }
}