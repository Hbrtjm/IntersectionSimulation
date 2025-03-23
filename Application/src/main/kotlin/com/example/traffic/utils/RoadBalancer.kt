package com.example.traffic.utils

class RoadBalancer {

    fun addCar(car: Car, roads: MutableList<Road>?)
    {
        roads?.filter { it.turnTypes.contains(getTurnType(car.startPoint,car.exitPoint)) } // Take only the roads where the car has to turn
        roads?.sortBy{it.vehicleCount()}
        roads?.get(0)?.add(car)
    }
}