package com.example.traffic.utils


interface TrafficLight<LightState> {

    /**
     *
     * For different light types the passage grant may be different. For regular lights green and yellow (usually) gives way.
     * For pedestrians, it's different - the blinking green and green light tells person crossing the street that the way is safe.
     * For tram crossings it's the blinking yellow light (advised caution). There is also a green conditional arrow.
     *
     *  @return If the traffic light grants passage for the car
     *
     */
    fun grantsPassage(): Boolean

    /**
     *
     * Sets the state of the light
     *
     * @param state Set the state of the traffic light
     *
     */
    fun setState(state: LightState)

    /**
     *
     * Switches lights to the next state
     *
     */
    fun nextLight()

    /**
     *
     * Returns the current state of the traffic light
     *
     * @returns LightState value
     *
     */
    fun getState(): LightState
}