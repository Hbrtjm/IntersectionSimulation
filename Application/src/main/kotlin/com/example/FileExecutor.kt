package com.example

import com.example.traffic.commands.FileHandler
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.FileNotFoundException

fun main(args: Array<String>)
{
    if(args.isEmpty())
    {
        println("""No argument provided!
            |Usage: java -jar FileExecutor.jar inputFile [outputFile]
        """.trimMargin())
        return
    }
    val inputFileName = args[0]
    var outputFileName: String? = null
    if (args.size > 1)
        outputFileName = args[1]
    try{
        val fileHandler = FileHandler(inputFileName,outputFileName)
        fileHandler.readData()
    } catch (e: FileNotFoundException) {
        println("The file does not exists!")
    } catch (e: Exception) {
        print("An error has occured: $e")
    }
}