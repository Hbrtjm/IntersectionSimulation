package com.example.traffic.simulation

import com.example.traffic.utils.Car
import com.example.traffic.utils.Direction
import com.example.traffic.utils.Road
import com.example.traffic.utils.RoadBalancer.addCar

/**
 *
 * Handles the queue of vehicles in the traffic simulation.
 *
 * This class is responsible for managing vehicles within an intersection by adding them to roads,
 * tracking their movement, and processing vehicle departures.
 *
 * @param roads A mutable map of road pairs (start direction to exit direction) associated with their respective road objects.
 *
 */
class VehicleQueueHandler(private val roads: MutableMap<Pair<Direction, Direction>, MutableList<Road>>) {

    private val carsSet: MutableSet<Car> = mutableSetOf() // Tracks vehicles currently in the system.

    /**
     *
     * Simulates vehicle movement on active roads in a single step.
     *
     * @param activeDirectionPairs A list of direction pairs representing roads with active traffic flow.
     * @return A list of vehicle IDs that have exited the intersection.
     *
     */
    fun step(activeDirectionPairs: List<Pair<Direction, Direction>>): List<String> {
        val leftVehicles: MutableList<String> = mutableListOf()

        for (activeDirection in activeDirectionPairs)
        {
            roads[activeDirection]?.let { roads ->
                roads.forEach { road ->
                    val movedCar = road.moveCars(activeDirection.first, activeDirection.second)
                    carsSet.remove(movedCar) // Remove the car from tracking once it has exited.
                    movedCar?.carID?.let { leftVehicles.add(it) } // Add the vehicle ID to the list of exited cars.
                }
            }
        }
        return leftVehicles
    }

    /**
     *
     * Adds a new vehicle to the traffic system.
     *
     * @param car The vehicle to be added.
     *
     */
    fun addVehicle(car: Car) {
        if (carsSet.contains(car)) {
            error("Car already exists in the set")
        }
        // This should ideally notify the caller about a potential ID collision instead of silently handling it.
        carsSet.add(car)
        addCar(car, roads[Pair(car.startPoint, car.exitPoint)]) // Assign the car to the appropriate road.
    }
}