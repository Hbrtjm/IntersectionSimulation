package com.example

import com.example.traffic.commands.Command
import com.example.traffic.commands.CommandProcessor
import com.example.traffic.commands.Commands
import com.example.traffic.commands.DebugModeController
import com.example.traffic.simulation.IntersectionManager
import com.example.traffic.utils.Direction
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.request.*

// TODO - Documentation and Comments, a lot of comments and refactoring, but at least we have the base application

fun main() {
    val intersectionManager = IntersectionManager()
    val commandProcessor = CommandProcessor();

    embeddedServer(Netty, port = 8080, host = "0.0.0.0", watchPaths = listOf("classes")) {
        if(DebugModeController.isDebugModeOn())
        {
            log.debug("================================ Debug mode ================================")
        }
        install(ContentNegotiation) {
            json()
        }
        routing {
            post("/commands") {
                val commands = call.receive<Commands>()
                // Process the commands in bulk and respond with the results
                val results = commandProcessor.runCommands(commands, call, intersectionManager)
                call.respond(HttpStatusCode.OK, results)
            }
        }
    }.start(wait = true)
}
