package com.example.traffic.utils

/**
 *
 * Specifies the possible turn directions a car can take at an intersection.
 *
 * A car approaching an intersection can make one of the following turns:
 * - FORWARD: The car continues straight ahead without changing direction.
 * - LEFT: The car turns left at the intersection.
 * - RIGHT: The car turns right at the intersection.
 * - TURN_AROUND: The car makes a U-turn to reverse its direction.
 *
 */
enum class TurnType {
    FORWARD, LEFT, RIGHT, TURN_AROUND
}
