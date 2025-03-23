package com.example.traffic.simulation

import com.example.traffic.utils.Car
import com.example.traffic.utils.Direction
import com.example.traffic.utils.Road
import com.example.traffic.utils.RoadBalancer
import kotlinx.serialization.json.*
import kotlinx.serialization.json.Json

/**
 * Manages the cars on each road
 */

class VehicleQueueHandler (private val roads : MutableMap<Direction, MutableList<Road>>) {
    private val roadBalancer = RoadBalancer()

    fun step(activeDirections: List<Direction>): List<String> {
        val leftVehicles: MutableList<String> = mutableListOf()

        for (activeDirection in activeDirections) {
            roads[activeDirection]?.let { roads ->
                roads.forEach { road ->
                    val movedCar = road.moveCars(activeDirection)
                    movedCar?.carID?.let { leftVehicles.add(it) } // Assuming 'movedCar' is the result of moveCars
                }
            }
        }
        return leftVehicles
    }

    fun addVehicle(car: Car)
    {
        // This here should inform the call stack about the possible problem of ID collision, maybe it should throw an error here
        roadBalancer.addCar(car, roads[car.startPoint]) // Keep it simple for now
    }
}