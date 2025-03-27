package com.example.traffic.utils

/**
 * Represents a Car with a unique identifier, a starting point on the intersection, and an exit point.
 *
 * @property carID A unique identifier for the car.
 * @property startPoint The direction from which the car enters.
 * @property exitPoint The direction in which the car exits.
 *
 * @constructor Creates a Car object with the specified ID, starting point, and exit point.
 *
 */
data class Car(val carID: String, val startPoint: Direction, val exitPoint: Direction) {

    override fun toString(): String {
        return "Car(carID='$carID', startPoint=$startPoint, exitPoint=$exitPoint)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Car

        return carID == other.carID
    }

    override fun hashCode(): Int {
        return carID.hashCode()
    }
}