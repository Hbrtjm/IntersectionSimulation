package com.example.traffic.utils

/**
 *
 * This class defines common behavior for traffic lights that control multiple roads.
 * It provides methods to register roads, manage their traffic flow, and notify them
 * when the light state changes.
 *
 * @param LightState The type representing the state of the traffic light.
 *
 */
abstract class AbstractTrafficLight<LightState> (protected open val turnTypes: List<TurnType>) : TrafficLight<LightState> {

    protected var subscribedRoads: MutableList<Road> = mutableListOf()

    /**
     *
     * Registers a single road to be controlled by this traffic light.
     *
     * @param road The road to be associated with this traffic light.
     *
     */
    fun registerRoad(road: Road) {
        subscribedRoads.add(road)
    }

    /**
     *
     * Registers multiple roads to be controlled by this traffic light. Uses registerRoad from this class, maybe it should be optimized.
     *
     * @param roads A list of roads to be associated with this traffic light.
     *
     */
    fun registerMultipleRoads(roads: List<Road>) {
        roads.forEach { registerRoad(it) }
    }

    /**
     *
     * Notifies subscribed roads when the traffic light state changes.
     *
     * If the light grants passage, the corresponding turn types are set as available for the roads.
     * Otherwise, the turn types are marked as unavailable.
     *
     */
    protected fun lightChanged() {
        subscribedRoads.forEach { road ->
            if (grantsPassage()) {
                road.setAvailableTurns(turnTypes)
            } else {
                road.unsetAvailableTurns(turnTypes)
            }
        }
    }
}
