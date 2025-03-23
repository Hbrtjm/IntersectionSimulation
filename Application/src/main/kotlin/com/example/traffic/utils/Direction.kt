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

fun parseDirection(stringDirection: String): Direction? {
    return when (stringDirection.lowercase()) {
        "north" -> Direction.NORTH
        "west" -> Direction.WEST
        "south" -> Direction.SOUTH
        "east" -> Direction.EAST
        else -> null
    }
}

fun mapDirection(direction: Direction) : Int
{
    return when (direction) {
        Direction.NORTH -> 0
        Direction.EAST -> 1
        Direction.SOUTH -> 2
        Direction.WEST -> 3
    }
}