package com.example.traffic.simulation

import com.example.traffic.utils.*
import com.example.traffic.utils.DirectionFunctions.mapDirection

/**
 *
 * This class analyzes the traffic. Collects the information about the traffic up to current state and responds to it.
 *
 */
class TrafficAnalyzer(private val roads: Map<Pair<Direction,Direction>, List<Road>>) {

    //
    // TODO - Too simple of an algorithm, I just get the road with highest count of cars. This could lead to 'starvation' of other roads.
    //  I think this is analogous to some computation of distributed systems...
    //
    fun getBusiestDirections(): List<Pair<Direction,Direction>>
    {
        val vehicleCounts = mutableListOf<Triple<Pair<Direction,Direction>, TurnType, Int>>()

        for ((directionPair, roadList) in roads)
        {
            for (road in roadList)
            {
                for (turnType in road.turnTypes)
                {
                    val count = road.vehicleCount()
                    vehicleCounts.add(Triple(directionPair, turnType, count))
                }
            }
        }

        vehicleCounts.sortByDescending { it.third }
        val maxCount = vehicleCounts.firstOrNull()?.third ?: return emptyList()
        if (maxCount == 0) return emptyList()
        val busiestRoads = vehicleCounts.filter { it.third == maxCount }
        val finalRoadDirections: MutableList<Pair<Direction,Direction>> = mutableListOf()
        //
        // TODO - O(n^2) runtime which probably can be optimized, but if we assume the count of roads is less than 1000, everything should be okay
        //
        for ((directionPair, turnType, _) in busiestRoads)
        {
            var collision = false
            for (existingDirectionPair in finalRoadDirections)
            {
                if (busiestRoads.any { checkCollision(directionPair.first, directionPair.second, existingDirectionPair.first, existingDirectionPair.second) } )
                {
                    collision = true
                    break
                }
            }
            if (!collision) {
                finalRoadDirections.add(directionPair)
            }
        }
        return finalRoadDirections.distinct()
    }

    /**
     *
     * Checks for collisions in given direcitons
     *
     * @param startFirst First car starting position on the intersection
     * @param endFirst First car destination position
     * @param startSecond Second car starting position
     * @param endSecond Second car destination position
     *
     * @return True if there is a collision between given directions, otherwise false
     *
     */
    fun checkCollision(startFirst: Direction, endFirst: Direction, startSecond: Direction, endSecond: Direction): Boolean
    {

        val startValueFirst = mapDirection(startFirst)
        val endValueFirst = mapDirection(endFirst)
        val startValueSecond = mapDirection(startSecond)
        val endValueSecond = mapDirection(endSecond)

        val differenceFirst = startValueFirst - endValueFirst
        val differenceSecond = startValueSecond - endValueSecond

        return when
        {
            startFirst == startSecond && endFirst == endSecond -> false
            startFirst == endSecond && startSecond == endFirst -> false
            (differenceFirst == 0 && differenceSecond == 0) -> false
            (differenceFirst % 2 == 0 && differenceSecond % 2 == 0) -> false
            else -> true
        }
    }
}
