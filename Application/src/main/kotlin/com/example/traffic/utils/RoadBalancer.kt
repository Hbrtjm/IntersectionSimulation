package com.example.traffic.utils

import com.example.traffic.utils.TurnTypeFunctions.getTurnType

/**
 *
 * Puts cars on the least busy roads, or it assigns them at random to spaces that match turn type to the car destination
 *
 */
object RoadBalancer {
    private var balanceMode: RoadBalancerMode = RoadBalancerMode.BALANCE

    fun setBalanceMode(newMode: RoadBalancerMode)
    {
        balanceMode = newMode
    }

    /**
     *
     * Assigns the car to the road with accordance to the set mode
     *
     * @param car Object of type car that will be added to the road
     * @param roads A list of roads to which we want to append the car. Problem arises when the car wants to go in an unsupported direction
     *
     */
    fun addCar(car: Car, roads: MutableList<Road>?)
    {
        roads?.filter { it.turnTypes.contains(getTurnType(car.startPoint,car.exitPoint)) } // Take only the roads where the car has to turn
        when (balanceMode) {
            RoadBalancerMode.RANDOM  -> { roads?.random()?.add(car) }
            RoadBalancerMode.BALANCE -> {
                // TODO - Instead of that I should use some sort of ordered set or a max heap
                roads?.sortBy{it.vehicleCount()}
                roads?.get(0)?.add(car)
            }
        }
    }
}