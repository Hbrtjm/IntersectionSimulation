package com.example.traffic.commands

import kotlinx.serialization.Serializable

@Serializable
data class Commands(val commands: List<Command>)
