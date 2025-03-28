package com.example

import com.example.traffic.commands.*
import com.example.traffic.simulation.IntersectionController
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.request.*
import java.io.FileNotFoundException
import java.nio.file.Paths
import com.example.traffic.commands.CommandProcessor.runCommands
import com.example.traffic.commands.CommandTranslator.convertStepResponses


fun main() {
    val intersectionController = IntersectionController()

    embeddedServer(Netty, port = 8080, host = "0.0.0.0", watchPaths = listOf("classes")) {
        if(DebugModeController.isDebugModeOn())
        {
            log.debug("================================ Debug mode ================================")
            val path = Paths.get("").toAbsolutePath().toString()
            log.debug(path)
        }
        install(ContentNegotiation) {
            json()
        }
        routing {
            post("/commands") {
                val commands = call.receive<Commands>()
                // Process the commands in bulk and respond with the results
                val results = runCommands(commands, intersectionController)
                if(!DebugModeController.isDebugModeOn())
                {
                    call.respond(HttpStatusCode.OK, StepStatusesResponse(convertStepResponses(results)))
                }
                else
                {
                    call.respond(HttpStatusCode.OK, results)
                }
            }
            route("/file")
            {
                get("/{inputFileName}")
                {
                    val inputFileName = call.parameters["inputFileName"]
                    try{
                        val fileHandler = FileHandler(inputFileName)
                        fileHandler.readData()
                        call.respond(HttpStatusCode.OK)
                    } catch (e: FileNotFoundException) {
                        call.respond(HttpStatusCode.NotFound, "This file does not exist")
                    } catch (e: Exception) {
                        call.respond(HttpStatusCode.InternalServerError, "An error occurred")
                    }
                }
                get("/{inputFileName}/{outPutFileName}")
                {
                    val inputFileName = call.parameters["inputFileName"]
                    val outputFileName = call.parameters["outputFileName"]
                    try{
                        val fileHandler = FileHandler(inputFileName,outputFileName)
                        fileHandler.readData()
                        call.respond(HttpStatusCode.OK)
                    } catch (e: FileNotFoundException) {
                        call.respond(HttpStatusCode.NotFound, "This file does not exist")
                    } catch (e: Exception) {
                        call.respond(HttpStatusCode.InternalServerError, "An error occurred")
                    }
                }
            }
        }
    }.start(wait = true)
//    val client = HttpClient(CIO) {
//        install(WebSockets)
//    }
//    runBlocking {
//        client.webSocket(
//            method = HttpMethod.Get,
//            host = "0.0.0.0",
//            port = 2137,
//            path = "/lightsStatus"
//        ) {
//            // this: DefaultClientWebSocketSession
//        }
//    }
}
