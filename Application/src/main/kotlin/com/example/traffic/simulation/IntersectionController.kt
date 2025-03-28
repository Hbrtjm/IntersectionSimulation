package com.example.traffic.simulation

import com.example.traffic.utils.*
import com.example.traffic.utils.TurnTypeFunctions.getDirection

/**
 * Controls and coordinates traffic flow at an intersection.
 *
 * This class manages algorithm flow for roads, traffic lights, and vehicle movement within an intersection.
 * It initializes a grid of roads and traffic lights, processes simulation steps, and
 * handles vehicle and road additions dynamically.
 *
 */
class IntersectionController {

    // Maps each pair of directions to a list of roads, since some roads may not handle certain maneuvers
    private val roads: MutableMap<Pair<Direction,Direction>, MutableList<Road>> = mutableMapOf()

    // Maps each pair of directions to a list of associated traffic lights, since some roads may not handle certain maneuvers.
    // Seems redundant, but unfortunately it's necessary, since roads above handle adding the car to the correct road, but here it's about control of the traffic directions.
    // That is, we do not control the activation of roads, but activation of traffic lights. Note that a road may contain more than one traffic light in different directions
    private val trafficLightsDirections: MutableMap<Pair<Direction,Direction>, MutableList<RegularTrafficLight>> = mutableMapOf()

    // Handles vehicle movement across the intersection. Appends vehicles to the road and removes the correct ones (standing on green lights) during simulation steps.
    private val vehicleQueue = VehicleQueueHandler(roads)

    // Controls the traffic lights at the intersection. It acts as a switch to the lights.
    private val regularTrafficLightController = RegularTrafficLightController(roads,trafficLightsDirections)

    // Generates all possible direction pairs for roads, unfortunate necessity to check the
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

    /**
     *
     * Advances the simulation by one step.
     *
     * This method updates the traffic lights and moves vehicles through the intersection.
     *
     * @return A list of vehicle IDs that have successfully moved through the intersection.
     *
     */
    fun simulationStep(): List<String> {
        val directions = regularTrafficLightController.step()
        return vehicleQueue.step(directions)
    }

    /**
     *
     * Adds a new vehicle to the intersection.
     *
     * The vehicle will be placed on the corresponding road based on its starting and ending directions.
     *
     * @param vehicleId A unique identifier for the vehicle.
     * @param start The starting direction of the vehicle.
     * @param end The intended exit direction of the vehicle.
     *
     */
    fun addVehicle(vehicleId: String, start: Direction, end: Direction) {
        vehicleQueue.addVehicle(Car(vehicleId, start, end))
    }

    /**
     *
     * Adds a new road to the intersection.
     *
     * Roads are added based on a direction and a list of allowed turn types.
     *
     * @param direction The direction of the road.
     * @param turnTypes The allowed turn types for the road.
     *
     */
    fun addRoad(direction: Direction, turnTypes: List<TurnType>?)
    {
        turnTypes?.forEach { turnType ->
            roads[Pair(direction, getDirection(turnType,direction))]?.add(Road(
                listOf(TurnType.FORWARD, TurnType.LEFT, TurnType.RIGHT)
            ))
        }
    }
}