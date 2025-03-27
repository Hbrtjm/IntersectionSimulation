package com.example.traffic.commands

import kotlinx.serialization.Serializable

/**
 *
 * A sealed class representing different types of step responses in the traffic simulation.
 *
 * This serves as a base class for various response types, such as `JsonStepResponse` and `ListStepResponse`,
 * which provide feedback on simulation steps. It allows for type safety and easy extension when adding new response types.
 *
 */
@Serializable
sealed class StepResponse