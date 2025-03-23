package com.example.traffic.utils

/**
 *
 * Can determine how many cars can go through in a step, for instance if we have two lanes, we can have
 * two cars leave times the amount of cars that can pass the intersection in one step
 * Each road should have a traffic light assigned
 *
 */

class Road constructor(subscribedTrafficLight: TrafficLight, turnTypes: List<TurnType>) {
    private val cars: MutableList<Car> = arrayListOf()
    val trafficLight = subscribedTrafficLight
    val turnTypes by lazy { turnTypes }
    fun add(car: Car)
    {
        cars.add(car)
    }
    fun vehicleCount(): Int
    {
        return cars.size
    }
    fun moveCars(direction: Direction): Car?
    {
        // If the traffic light is green and that we are headed in the right direction,
        // for now I assume we are, but road should have its own unique set of directions
        if(trafficLight.isGreen())
        {
            val movedCar = cars[0]
            cars.removeAt(0)
            return movedCar
        }
        return null;
    }
}