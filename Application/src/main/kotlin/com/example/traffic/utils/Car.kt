package com.example.traffic.utils

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
