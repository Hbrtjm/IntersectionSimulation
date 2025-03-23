package com.example.traffic.simulation

import com.example.traffic.utils.Direction
import com.example.traffic.utils.Road
import com.example.traffic.utils.TrafficLightState

/**
 * Controls separate traffic lights
 */

class TrafficLightController(private val roads: Map<Direction, List<Road>>) {
    val activeRoads = arrayListOf<Direction>()

    fun step() {
        val busiestDirections = TrafficAnalyzer(roads).getBusiestDirections()
        activeRoads.clear()

        for ((direction, roadList) in roads) {
            for (road in roadList) {
                val trafficLight = road.trafficLight
                if (busiestDirections.contains(direction)) {
                    trafficLight.setTrafficLightState(TrafficLightState.GREEN)
                    activeRoads.add(direction)
                } else {
                    trafficLight.setTrafficLightState(TrafficLightState.RED)
                }
            }
        }
    }
}