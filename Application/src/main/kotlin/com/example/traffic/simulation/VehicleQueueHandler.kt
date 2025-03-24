package com.example.traffic.simulation

import com.example.traffic.utils.Car
import com.example.traffic.utils.Direction
import com.example.traffic.utils.Road
import com.example.traffic.utils.RoadBalancer

/**
 * Manages the cars on each road
 */

class VehicleQueueHandler (private val roads : MutableMap<Pair<Direction,Direction>, MutableList<Road>>) {
    private val roadBalancer = RoadBalancer()
    private val carsSet: MutableSet<Car> = mutableSetOf()
    fun step(activeDirectionPairs: List<Pair<Direction,Direction>>): List<String> {
        val leftVehicles: MutableList<String> = mutableListOf()

        for (activeDirection in activeDirectionPairs)
        {
            roads[activeDirection]?.let { roads ->
                roads.forEach { road ->
                    val movedCar = road.moveCars(activeDirection.first, activeDirection.second)
                    carsSet.remove(movedCar)
                    movedCar?.carID?.let { leftVehicles.add(it) }
                }
            }
        }
        return leftVehicles
    }

    fun addVehicle(car: Car)
    {
        if(carsSet.contains(car))
        {
            error("Car already exists in the set")
        }

        // This here should inform the call stack about the possible problem of ID collision, maybe it should throw an error here
        carsSet.add(car)
        roadBalancer.addCar(car, roads[Pair(car.startPoint,car.exitPoint)]) // Keep it simple for now
    }
}