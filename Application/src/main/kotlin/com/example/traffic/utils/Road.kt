package com.example.traffic.utils

import com.example.traffic.utils.TurnTypeFunctions.getTurnType

/**
 *
 * Represents a road that has cars, a traffic light, and specific turn types for vehicle movement.
 * Handles the vehicles on the road, the associated traffic light(s), and the turn directions
 * for each vehicle at an intersection.
 *
 * - subscribedRegularTrafficLight: A traffic light that manages the flow of traffic on this road.
 * - turnTypes: A list specifying the possible directions in which vehicles can turn.
 *
 * The road can process cars that are queued to pass through the intersection, based on the state of the traffic light
 * and the allowed turn types. The moveCars method will allow cars to pass if the light is green and the direction is valid.
 *
 */
class Road(turnTypes: List<TurnType>) {
    val cars: MutableList<Car> = arrayListOf()  // A list of cars currently on the road.
    val turnTypes by lazy { turnTypes }  // The list of possible turn directions for cars on this road.
    private var activeDirections: HashMap<TurnType,Int> = hashMapOf()

    // The logic behind this, is that there might be an omnidirectional light that will get activated/deactivated, when the other one is not
    fun setAvailableTurns(turnTypes: List<TurnType>)
    {
        turnTypes.forEach { turnType -> if (activeDirections.containsKey(turnType)) {
            activeDirections[turnType] = activeDirections[turnType]!! + 1
        } else { activeDirections[turnType] = 1 } }
    }

    fun unsetAvailableTurns(turnTypes: List<TurnType>)
    {
        turnTypes.forEach { turnType -> if (activeDirections.containsKey(turnType) ) { if(activeDirections[turnType]!! > 1) {
            activeDirections[turnType] = activeDirections[turnType]!! - 1 }
        } else { activeDirections.remove(turnType) } }
    }

    /**
     *
     * Adds a car to the road's queue.
     *
     * This method assumes the car has a unique ID, which is managed by the VehicleQueue.
     *
     * @param car The car to be added to the road's queue.
     *
     */
    fun add(car: Car) {
        cars.add(car)
    }

    /**
     *
     * Returns the number of cars currently waiting to move on the road.
     *
     * @return The number of cars on the road.
     *
     */
    fun vehicleCount(): Int {
        return cars.size
    }

    /**
     *
     * Attempts to move the first car in the queue based on the traffic light state and turn direction.
     * The car can only move if the traffic light is green and if the direction of movement is allowed
     * based on the road's specified turn types. For now, the direction logic is simplified but should
     * be expanded to handle specific road directions and traffic rules.
     *
     * @param startDirection The direction the car is currently approaching from.
     * @param endDirection The direction the car intends to move towards.
     * @return The moved car, or `null` if the car couldn't move (e.g., the light isn't green or the direction is invalid).
     *
     */
    fun moveCars(startDirection: Direction, endDirection: Direction): Car?
    {
        if (activeDirections.contains(getTurnType(startDirection, endDirection)))
        {
            if(cars.size == 0)
            {
                return null
            }
            val movedCar = cars[0]
            cars.removeAt(0)
            return movedCar
        }
        return null
    }

    override fun toString(): String {
        return "Road(cars=$cars, turnTypes=$turnTypes)"
    }
}
