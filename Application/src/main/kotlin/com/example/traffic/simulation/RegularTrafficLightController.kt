package com.example.traffic.simulation

import com.example.traffic.utils.*
import java.util.*

/**
 *
 * Controls the traffic lights for various roads and manages the switching between different light states.
 *
 * This class is responsible for controlling the traffic lights at different intersections based on the busiest directions
 * determined by the `TrafficCounterController`. It manages a queue of traffic lights and sets their states accordingly.
 *
 * @param roads A map of road direction pairs to their corresponding list of roads. This helps in identifying the road directions.
 * @param trafficLights A map of road direction pairs to their corresponding list of traffic lights. This helps in controlling the traffic lights for each direction pair.
 *
 */
class RegularTrafficLightController(private val roads: Map<Pair<Direction,Direction>, List<Road>>, private val trafficLights: Map<Pair<Direction,Direction> ,List<RegularTrafficLight>>) {
    private var switchesQueue: Queue<List<RegularTrafficLight>> = ArrayDeque()
    var activeRoads: MutableList<Pair<Direction,Direction>> = mutableListOf()

    /**
     *
     * This method adds new traffic lights to the queue that will be active in the next step.
     *
     * @param lights A list of traffic lights to activate next.
     *
     */
    fun addControlPoint(lights: List<RegularTrafficLight>)
    {
        switchesQueue.offer(lights)
    }

    /**
     *
     * Performs a simulation step to control the traffic lights based on the busiest directions.
     *
     * This method updates the state of each traffic light, turning the lights green for the busiest directions
     * and setting other directions accordingly. It returns the list of roads with active traffic lights.
     *
     * @return A list of roads (direction pairs) with active green traffic lights.
     *
     */
    fun step(): List<Pair<Direction,Direction>> {
        val busiestDirections = TrafficCounterController(roads).getBusiestDirections()
        activeRoads.clear()
        for ((directionPair, _) in roads) {
                if (busiestDirections.contains(directionPair)) {
                    trafficLights[directionPair]?.forEach { trafficLight -> trafficLight.setState(RegularTrafficLightState.GREEN) }
                    activeRoads.add(directionPair)
                }
                else // This should be handled previously
                {
                    trafficLights[directionPair]?.forEach { trafficLight -> trafficLight.setState(RegularTrafficLightState.GREEN) }
                }
        }
        return activeRoads.distinct()
    }
}