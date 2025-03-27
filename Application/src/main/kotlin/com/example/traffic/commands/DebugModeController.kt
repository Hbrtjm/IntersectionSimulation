package com.example.traffic.commands

/**
 *
 * Manages the debug mode settings.
 *
 * The debug mode can be turned on or off based on an environment variable (`DEBUG_MODE`).
 *
 */
object DebugModeController {

    /**
     *
     * Checks whether the debug mode is enabled.
     *
     * @return True if debug mode is enabled, false otherwise.
     *
     */
    fun isDebugModeOn(): Boolean
    {
        return System.getenv("DEBUG_MODE")?.toBoolean() ?: false
    }
}
