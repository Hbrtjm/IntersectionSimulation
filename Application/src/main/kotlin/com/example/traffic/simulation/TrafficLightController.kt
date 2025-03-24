package com.example.traffic.simulation

import com.example.traffic.utils.Direction
import com.example.traffic.utils.Road
import com.example.traffic.utils.TrafficLightState

/**
 * Controls separate traffic lights
 */

class TrafficLightController(private val roads: Map<Pair<Direction,Direction>, List<Road>>) {
    val activeRoads = arrayListOf<Pair<Direction,Direction>>()

    fun step() {
        val busiestDirections = TrafficAnalyzer(roads).getBusiestDirections()
        activeRoads.clear()

        for ((directionPair, roadList) in roads) {
            for (road in roadList) {
                val trafficLight = road.trafficLight
                if (busiestDirections.contains(directionPair)) {
                    trafficLight.setTrafficLightState(TrafficLightState.GREEN)
                    activeRoads.add(directionPair)
                } else {
                    trafficLight.setTrafficLightState(TrafficLightState.RED)
                }
            }
        }
    }
}