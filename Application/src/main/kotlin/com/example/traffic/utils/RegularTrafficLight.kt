package com.example.traffic.utils

import com.example.traffic.utils.TurnTypeFunctions.getDirection
/**
 *
 * Represents a regular traffic light that follows a predefined sequence of states.
 *
 * @property turnTypes A list of turn types that this traffic light supports.
 *
 */
class RegularTrafficLight(override val turnTypes: List<TurnType>) : AbstractTrafficLight<RegularTrafficLightState>(turnTypes) {

    /** The current state of the traffic light, initialized to RED. */
    var _trafficLightState: RegularTrafficLightState = RegularTrafficLightState.RED

    /**
     *
     * Retrieves the current state of the traffic light.
     *
     * @return The current state of the traffic light.
     *
     */
    override fun getState(): RegularTrafficLightState = _trafficLightState

    /**
     *
     * Updates the state of the traffic light.
     *
     * @param state The new state to set for the traffic light.
     *
     */
    override fun setState(state: RegularTrafficLightState)
    {
        if(_trafficLightState != state)
        {
            _trafficLightState = state
            lightChanged()
        }
    }

    /**
     *
     * Determines whether the traffic light grants passage based on its current state.
     * Green and yellow lights allow passage, as defined in the RegularTrafficLightState enum.
     *
     * @return True if the light is GREEN or YELLOW, otherwise false.
     *
     */
    override fun grantsPassage(): Boolean
    {
        return _trafficLightState == RegularTrafficLightState.GREEN || _trafficLightState == RegularTrafficLightState.YELLOW
    }

    /**
     * Transitions the traffic light to the next state in its sequence.
     *
     * The order of states is as follows:
     * - RED -> RED_YELLOW
     * - RED_YELLOW -> GREEN
     * - GREEN -> YELLOW
     * - YELLOW -> RED
     * - BROKEN remains unchanged.
     *
     */
    override fun nextLight()
    {
        _trafficLightState = when (_trafficLightState)
        {
            RegularTrafficLightState.RED        -> RegularTrafficLightState.RED_YELLOW
            RegularTrafficLightState.RED_YELLOW -> RegularTrafficLightState.GREEN
            RegularTrafficLightState.GREEN      -> RegularTrafficLightState.YELLOW
            RegularTrafficLightState.YELLOW     -> RegularTrafficLightState.RED
            RegularTrafficLightState.BROKEN     -> RegularTrafficLightState.BROKEN
        }
        // If it's not broken, then surely the state has changed
        if(_trafficLightState == RegularTrafficLightState.GREEN && _trafficLightState == RegularTrafficLightState.RED)
        {
            lightChanged()
        }
    }

    override fun equals(other: Any?): Boolean
    {
        if (this === other) return true
        if (other !is RegularTrafficLight) return false

        return turnTypes == other.turnTypes && _trafficLightState == other._trafficLightState
    }

    override fun hashCode(): Int
    {
        return 31 * turnTypes.hashCode() + _trafficLightState.hashCode()
    }
}
