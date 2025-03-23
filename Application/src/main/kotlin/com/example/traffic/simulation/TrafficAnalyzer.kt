package com.example.traffic.simulation

import com.example.traffic.utils.*

/**
 * This class analyzes the traffic. Collects the information about the traffic up to current state and responds to it.
 */


class TrafficAnalyzer(private val roads: Map<Direction, List<Road>>) {
    //
    // TODO - Too simple of an algorithm, I just get the road with highest count of cars. This could lead to 'starvation' of other roads.
    //  I think this is analogous to some computation of distributed systems...
    //
    fun getBusiestDirections(): List<Direction> {
        val vehicleCounts = mutableListOf<Triple<Direction, TurnType, Int>>()

        for ((direction, roadList) in roads) {
            for (road in roadList) {
                for (turnType in road.turnTypes) {
                    val count = road.vehicleCount()
                    vehicleCounts.add(Triple(direction, turnType, count))
                }
            }
        }

        vehicleCounts.sortByDescending { it.third }
        val maxCount = vehicleCounts.firstOrNull()?.third ?: return emptyList()
        val busiestRoads = vehicleCounts.filter { it.third == maxCount }
        val finalRoadDirections: MutableList<Direction> = mutableListOf()
        //
        // TODO - O(n^2) runtime which probably can be optimized, but if we assume the count of roads is less than 1000, everything should be okay
        //
        for ((direction, turnType, _) in busiestRoads) {
            if (finalRoadDirections.isEmpty()) {
                finalRoadDirections.add(direction)
                continue
            }
            var collision = false
            for (existingDirection in finalRoadDirections) {
                if (busiestRoads.any { checkCollision(direction, getDirection(turnType, direction), existingDirection, getDirection(it.second, it.first)) }) {
                    collision = true
                    break
                }
            }
            if (collision) {
                finalRoadDirections.add(direction)
            }
        }
        return finalRoadDirections
    }

    private fun checkCollision(startFirst: Direction, endFirst: Direction, startSecond: Direction, endSecond: Direction): Boolean {

        val startValueFirst = mapDirection(startFirst)
        val endValueFirst = mapDirection(endFirst)
        val startValueSecond = mapDirection(startSecond)
        val endValueSecond = mapDirection(endSecond)

        val differenceFirst = startValueFirst - endValueFirst
        val differenceSecond = startValueSecond - endValueSecond

        return when {
            startFirst == startSecond && endFirst == endSecond -> false
            startFirst == endSecond && startSecond == endFirst -> false
            (differenceFirst == 0 && differenceSecond == 0) -> false
            (differenceFirst % 2 == 0 && differenceSecond % 2 == 0) -> false
            else -> true
        }
    }
}
