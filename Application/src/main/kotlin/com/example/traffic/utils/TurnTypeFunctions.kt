package com.example.traffic.utils

object TurnTypeFunctions {
    /**
     *
     * Determines the opposite direction of the given direction.
     *
     * @param direction The current direction.
     * @return The direction directly opposite to the given one.
     *
     */
    fun getDirectionForward(direction: Direction): Direction {
        return when (direction) {
            Direction.NORTH -> Direction.SOUTH
            Direction.EAST -> Direction.WEST
            Direction.SOUTH -> Direction.NORTH
            Direction.WEST -> Direction.EAST
        }
    }

    /**
     *
     * Determines the direction when making a left turn.
     *
     * @param direction The current direction.
     * @return The new direction after a left turn.
     *
     */
    fun getDirectionLeft(direction: Direction): Direction {
        return when (direction) {
            Direction.NORTH -> Direction.EAST
            Direction.EAST -> Direction.SOUTH
            Direction.SOUTH -> Direction.WEST
            Direction.WEST -> Direction.NORTH
        }
    }

    /**
     *
     * Determines the direction when making a right turn.
     *
     * @param direction The current direction.
     * @return The new direction after a right turn.
     *
     */
    fun getDirectionRight(direction: Direction): Direction {
        return when (direction) {
            Direction.NORTH -> Direction.WEST
            Direction.EAST -> Direction.NORTH
            Direction.SOUTH -> Direction.EAST
            Direction.WEST -> Direction.SOUTH
        }
    }

    /**
     *
     * Returns the same direction when making a U-turn.
     *
     * @param direction The current direction.
     * @return The same direction (as a U-turn).
     *
     */
    fun getDirectionTurnaround(direction: Direction): Direction {
        return direction
    }

    /**
     *
     * Determines the new direction based on the turn type.
     *
     * @param type The type of turn to be made.
     * @param direction The current direction.
     * @return The new direction after applying the turn.
     *
     */
    fun getDirection(type: TurnType, direction: Direction): Direction {
        return when (type) {
            TurnType.FORWARD -> getDirectionForward(direction)
            TurnType.LEFT -> getDirectionLeft(direction)
            TurnType.RIGHT -> getDirectionRight(direction)
            TurnType.TURN_AROUND -> getDirectionTurnaround(direction)
        }
    }

    /**
     *
     * Computes the turn type required to transition from one direction to another.
     *
     * @param start The starting position of the car
     * @param end The final direction of the car
     * @return The type of turn needed to go from `start` to `end`.
     *
     */
    fun getTurnType(start: Direction, end: Direction): TurnType {
        val startValue = DirectionFunctions.mapDirection(start)
        val endValue = DirectionFunctions.mapDirection(end)

        val difference = endValue - startValue + 4

        return when (difference % 4) {
            0 -> TurnType.TURN_AROUND
            1 -> TurnType.LEFT
            3 -> TurnType.RIGHT
            else -> TurnType.FORWARD
        }
    }
}