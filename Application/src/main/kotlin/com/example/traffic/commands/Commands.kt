package com.example.traffic.commands

import kotlinx.serialization.Serializable

/**
 *
 * A collection of commands to be executed.
 *
 * @property commands A list of `Command` objects that will be processed sequentially.
 *
 */
@Serializable
data class Commands(val commands: List<Command>)
