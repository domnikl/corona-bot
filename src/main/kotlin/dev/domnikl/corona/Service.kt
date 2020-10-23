package dev.domnikl.corona

import com.typesafe.config.ConfigFactory
import java.io.File

fun main(args: Array<String>) {
    require(args.isNotEmpty()) { "Please provide a config file" }

    val config = ConfigFactory.parseFile(File(args[0]))
    val factory = Factory(config)
    val notificationChannel = factory.notificationChannel
    val dataSource = factory.dataSource
    val chatMessageBuilder = ChatMessageBuilder()

    val message = try {
        dataSource.get().map { report ->
            chatMessageBuilder.build(report)
        }
    } catch (e: Exception) {
        listOf(":warning: Konnte den heutigen Corona-Report wegen eines Fehlers nicht erzeugen: ${e.message}")
    }.joinToString("\n\n")

    notificationChannel.sendMessage("$message\n\nBleibt bitte gesund! :sunny:").queue()

    factory.jda.shutdown()
}
