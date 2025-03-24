package com.example.traffic.utils

/**
 *
 * Specifies the direction of the road. Will be used for:
 *  - To tell the Car's starting and ending position
 *  - To specify where the traffic light could potentially lead
 *
 */

enum class Direction {
    NORTH, WEST, SOUTH, EAST
}

fun mapDirection(direction: Direction) : Int
{
    // Set North -> 0, East -> 1, South -> 2, West -> 3
    return when (direction) {
        Direction.NORTH -> 0
        Direction.EAST -> 1
        Direction.SOUTH -> 2
        Direction.WEST -> 3
    }
}