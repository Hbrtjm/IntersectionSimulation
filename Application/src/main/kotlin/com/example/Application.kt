package com.example

import com.example.traffic.commands.StepStatus
import com.example.traffic.commands.*
import com.example.traffic.simulation.IntersectionManager
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.request.*
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonPrimitive
import java.io.FileNotFoundException
import java.nio.file.Paths
import com.example.traffic.commands.CommandProcessor.runCommands


//import io.ktor.client.*
////import io.ktor.client.engine.cio.*
//import io.ktor.client.plugins.websocket.*
//import kotlinx.coroutines.runBlocking

// TODO - Documentation and Comments, a lot of comments and refactoring, but at least we have the base application

fun main() {
   fun convertStepResponses(stepResponses: List<StepResponse>): List<StepStatus> {
        return stepResponses.mapNotNull { stepResponse ->
            if (stepResponse is JsonStepResponse) {
                val jsonObject = stepResponse.response as? JsonObject
                val carsLeft = jsonObject?.get("carsLeft")?.jsonArray?.map { it.jsonPrimitive.content } ?: emptyList()
                StepStatus(carsLeft)
            } else {
                null
            }
        }
    }

    val intersectionManager = IntersectionManager()

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
                val results = runCommands(commands, intersectionManager)
                if(!DebugModeController.isDebugModeOn())
                {
//                    val finalResponse = StepStatusesResponse(results.map { it as JsonStepResponse })
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
                    val inputFileName = call.parameters["inputFileName"]!! + ".json"
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
                    val inputFileName = call.parameters["inputFileName"]!! + ".json"
                    val outputFileName = call.parameters["outputFileName"]!! + ".json"
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
