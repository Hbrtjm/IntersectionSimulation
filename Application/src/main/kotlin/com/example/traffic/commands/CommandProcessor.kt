package com.example.traffic.commands

import com.example.traffic.simulation.IntersectionManager

/**
 *
 * Responsible for processing a list of commands and applying them to the intersection manager.
 *
 * It executes each command sequentially, using CommandTranslator to translate the command into actions
 * and stores the result in a list of StepResponses.
 *
 */
object CommandProcessor {

    /**
     *
     * Processes a list of commands and applies them to the intersection manager.
     *
     * @param commands The list of commands to be executed.
     * @param intersectionManager The intersection manager to apply the commands to.
     * @return A list of step responses generated after executing the commands.
     *
     */
    fun runCommands(commands: Commands, intersectionManager: IntersectionManager): List<StepResponse>
    {
        val stepResults = mutableListOf<StepResponse>()

        commands.commands.forEach { command ->
            CommandTranslator.translateCommand(
                command.type,
                command.startRoad,
                command.endRoad,
                command.vehicleId,
                command.direction,
                command.turnTypes,
                stepResults,
                intersectionManager
            )
        }

        return stepResults
    }
}
