package com.example.traffic.utils

class TrafficLight constructor (private val types: List<TurnType>)
{
    private var trafficLightState = parseTrafficLight("RED")

    fun getTrafficLightDirection(direction: Direction): MutableList<Direction>
    {
        val result: MutableList<Direction> = arrayListOf()
        for(type in types)
        {
            result.add(getDirection(type,direction))
        }
        return result
    }

    fun setTrafficLightState(newTrafficLightState: TrafficLightState)
    {
        trafficLightState = newTrafficLightState
    }

    fun isGreen(): Boolean
    {
        return trafficLightState == TrafficLightState.GREEN
    }

}