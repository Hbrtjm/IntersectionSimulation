package com.example.traffic.simulation

import com.example.traffic.utils.*
import java.util.*

/**
 * Controls separate traffic lights
 *
 * @param
 *
 */
class RegularTrafficLightController(private val roads: Map<Pair<Direction,Direction>, List<Road>>, private val trafficLights: Map<Pair<Direction,Direction> ,List<RegularTrafficLight>>) {
    private var switchesQueue: Queue<List<RegularTrafficLight>> = ArrayDeque()
    public var activeRoads: MutableList<Pair<Direction,Direction>> = mutableListOf()

    fun addControlPoint(lights: List<RegularTrafficLight>)
    {
        switchesQueue.offer(lights)
    }

    fun step(): List<Pair<Direction,Direction>> {
        // Caused problems, working on a solution...
        //        repeat(2) {
        //            // On first iteration turns to yellow, but without a WebSocket or any real-time update on the frontend side,
        //            // this will not have any visible effect. Nevertheless, it might unexpectedly come in handy later
        //            activeLights.forEach { regularTrafficLight -> regularTrafficLight.nextLight() }
        //        }
        val busiestDirections = TrafficAnalyzer(roads).getBusiestDirections()

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
        return activeRoads
    }
}