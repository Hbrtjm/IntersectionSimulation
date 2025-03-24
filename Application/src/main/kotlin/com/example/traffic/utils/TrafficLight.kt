package com.example.traffic.utils

class TrafficLight (private val types: List<TurnType>)
{
    public var trafficLightCurrentState = parseTrafficLight("RED")

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
        trafficLightCurrentState = newTrafficLightState
    }

    fun isGreen(): Boolean
    {
        return trafficLightCurrentState == TrafficLightState.GREEN
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TrafficLight

        if (types != other.types) return false
        if (trafficLightCurrentState != other.trafficLightCurrentState) return false

        return true
    }

    override fun hashCode(): Int {
        var result = types.hashCode()
        result = 31 * result + (trafficLightCurrentState?.hashCode() ?: 0)
        return result
    }

}