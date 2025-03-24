package com.example.traffic.commands

object DebugModeController {
    fun isDebugModeOn(): Boolean
    {
        return System.getenv("DEBUG_MODE")?.toBoolean() ?: false
    }
}