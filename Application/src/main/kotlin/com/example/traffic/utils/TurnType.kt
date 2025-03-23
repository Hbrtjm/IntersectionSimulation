package com.example.traffic.utils

enum class TurnType {
    FORWARD, LEFT, RIGHT, TURN_AROUND
}

fun getDirectionForward(direction: Direction): Direction
{
    return when (direction) {
        Direction.NORTH -> Direction.SOUTH
        Direction.EAST -> Direction.WEST
        Direction.SOUTH -> Direction.NORTH
        Direction.WEST -> Direction.EAST
    }
}

fun getDirectionLeft(direction: Direction): Direction
{
    return when (direction) {
        Direction.NORTH -> Direction.EAST
        Direction.EAST -> Direction.SOUTH
        Direction.SOUTH -> Direction.WEST
        Direction.WEST -> Direction.NORTH
    }
}

fun getDirectionRight(direction: Direction): Direction
{
    return when (direction) {
        Direction.NORTH -> Direction.WEST
        Direction.EAST -> Direction.NORTH
        Direction.SOUTH -> Direction.EAST
        Direction.WEST -> Direction.SOUTH
    }
}

fun getDirectionTurnaround(direction: Direction): Direction
{
    return direction
}

fun getDirection(type: TurnType, direction: Direction): Direction
{
    return when (type) {
        TurnType.FORWARD -> getDirectionForward(direction)
        TurnType.LEFT -> getDirectionLeft(direction)
        TurnType.RIGHT -> getDirectionRight(direction)
        TurnType.TURN_AROUND -> getDirectionTurnaround(direction)
    }
}

fun getTurnType(start: Direction, end: Direction): TurnType
{
    val startValue = mapDirection(start)
    val endValue = mapDirection(end)

    val difference = endValue - startValue

    return when {
        difference == 0 -> TurnType.TURN_AROUND
        difference % 2 != 0 && difference < 0 -> TurnType.LEFT
        difference % 2 != 0 && difference > 0 -> TurnType.RIGHT
        else -> TurnType.FORWARD
    }
}
