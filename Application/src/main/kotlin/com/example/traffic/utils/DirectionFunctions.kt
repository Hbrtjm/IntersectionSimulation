package com.example.traffic.utils

object DirectionFunctions {
    /**
     *
     * Translates direction value into a number
     *
     * @param direction One of the directions, numbering runs in order NORTH, EAST, SOUTH, WEST (NORTH and clockwise)
     * @return The number assigned starting from 0 counting in the order described in parameters
     *
     */
    fun mapDirection(direction: Direction) : Int
    {
        return when (direction) {
            Direction.NORTH -> 0
            Direction.EAST -> 1
            Direction.SOUTH -> 2
            Direction.WEST -> 3
        }
    }
}