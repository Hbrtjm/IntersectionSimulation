package com.example.traffic.utils

/**
 *
 * Represents a regular traffic light that follows a predefined sequence of states.
 *
 * @property turnTypes A list of turn types that this traffic light supports.
 *
 */
class RegularTrafficLight(override val turnTypes: List<TurnType>) : AbstractTrafficLight<RegularTrafficLightState>(turnTypes) {

    /** The current state of the traffic light, initialized to RED. */
    var trafficLightState: RegularTrafficLightState = RegularTrafficLightState.RED

    /**
     *
     * Retrieves the current state of the traffic light.
     *
     * @return The current state of the traffic light.
     *
     */
    override fun getState(): RegularTrafficLightState = trafficLightState

    /**
     *
     * Updates the state of the traffic light.
     *
     * @param state The new state to set for the traffic light.
     *
     */
    override fun setState(state: RegularTrafficLightState)
    {
        if(trafficLightState != state)
        {
            trafficLightState = state
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
        return trafficLightState == RegularTrafficLightState.GREEN || trafficLightState == RegularTrafficLightState.YELLOW
    }

    /**
     * Transitions the traffic light to the next state in its sequence.
     * Only emits lightChanged() when light changed to GREEN or RED, as these are the states that change how the car behaves. However, if I were to implement a real-time websocket that updates
     * traffic lights on the frontend, I would need to emit another call that will inform the server to send new information.
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
        trafficLightState = when (trafficLightState)
        {
            RegularTrafficLightState.RED        -> RegularTrafficLightState.RED_YELLOW
            RegularTrafficLightState.RED_YELLOW -> RegularTrafficLightState.GREEN
            RegularTrafficLightState.GREEN      -> RegularTrafficLightState.YELLOW
            RegularTrafficLightState.YELLOW     -> RegularTrafficLightState.RED
            RegularTrafficLightState.BROKEN     -> RegularTrafficLightState.BROKEN
        }
        // If it's not broken, then surely the state has changed
        if(trafficLightState == RegularTrafficLightState.GREEN && trafficLightState == RegularTrafficLightState.RED)
        {
            lightChanged()
        }
    }

    override fun equals(other: Any?): Boolean
    {
        if (this === other) return true
        if (other !is RegularTrafficLight) return false

        return turnTypes == other.turnTypes && trafficLightState == other.trafficLightState
    }

    override fun hashCode(): Int
    {
        return 31 * turnTypes.hashCode() + trafficLightState.hashCode()
    }
}
