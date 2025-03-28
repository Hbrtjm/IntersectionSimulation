package com.example.traffic.simulation

import com.example.traffic.utils.*
import com.example.traffic.utils.DirectionFunctions.mapDirection

/**
 * Analyzes traffic data to identify the busiest road directions.
 *
 * This class collects vehicle count information from all the roads in the intersection
 * and determines which road directions have the highest traffic volume. It iterates over each
 * road and each allowed turn type to aggregate vehicle counts and then selects the direction pairs
 * that register the highest count. If multiple roads share the highest vehicle count, it performs a
 * collision check to ensure that overlapping directions are not repeatedly added.
 *
 * NOTE: The current algorithm is very simple, selecting roads solely based on the highest vehicle count.
 * This may lead to potential starvation of less busy roads. There is room for improvement in balancing
 * the traffic load.
 *
 * @param roads A map where each key is a pair of [Direction] objects representing a road's entry and exit,
 * and each value is a list of [Road] objects associated with that direction pair.
 */
class TrafficCounterController(private val roads: Map<Pair<Direction,Direction>, List<Road>>) {

    /**
     * Identifies and returns the busiest road direction pairs based on vehicle counts.
     *
     * The method performs the following steps:
     * 1. Iterates over all roads and their allowed turn types to build a list of triples,
     *    each containing a road direction pair, a [TurnType], and the corresponding vehicle count.
     * 2. Sorts the list of vehicle counts in descending order.
     * 3. Filters out the direction pairs with the highest vehicle count.
     * 4. Checks for collisions between direction pairs to avoid adding overlapping directions.
     *
     * @return A list of distinct direction pairs (`Pair<Direction, Direction>`) representing the busiest roads.
     */
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
        for ((directionPair, _, _) in busiestRoads)
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
     * A helper function to collision check. I par the intersection into from below:
     *
     *   |      |      |
     * __|             |__
     *       0     1
     * --               --
     *       3     2
     * __               __
     *   |      |      |
     *
     *   If the car crosses a given sector, I put a 1 in the binary representation of the number. The indexes on the road are the same as the indexes in the number.
     *   I assume first that we start at North, the mapping in when statement corresponds to the mapping for North maneuvers. Then I simply cyclically shift bits
     *   clockwise, effectively I bitshift the number to the left. Below there is a vizualisation of this process.
     *
     *   * -> 01 -> 30 -> 23 -> 12 -> *
     *   * -> 32 -> 21 -> 10 -> 03 -> *
     *
     *   @param value Difference between direction values, as in mapDirection()
     *   @param position Starting position for the maneuver
     *
     *   @return The binary representation of the maneuver on the intersection
     *
     */
    private fun mapValue(value: Int, position: Direction): Int
    {
        val initialValue = when (value)
        {
            -3 -> 14
            -2 -> 6
            -1 -> 13
            0 -> 3
            1 -> 2
            2 -> 9
            3 -> 1
            else -> -1
        }
        return (initialValue shr mapDirection(position)) or ((initialValue shl (4 - mapDirection(position))) % 16)
    }

    /**
     *
     * Checks for collisions in given directions
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

        val first = mapValue(differenceFirst, startFirst)
        val second =  mapValue(differenceSecond, startSecond)

        if(first == second)
        {
            return false
        }
        return (first and second) != 0
    }
}
