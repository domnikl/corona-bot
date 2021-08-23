package dev.domnikl.corona

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.typesafe.config.Config
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.TextChannel
import okhttp3.OkHttpClient
import java.net.URL
import java.util.concurrent.TimeUnit

class Factory(config: Config) {
    private val httpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .readTimeout(1, TimeUnit.MINUTES)
            .build()
    }

    val dataSource: DataSource by lazy {
        RobertKochDataSource(
            httpClient,
            ObjectMapper().registerKotlinModule(),
            URL(config.getString("robertKochDataSource.url")),
            config.getStringList("robertKochDataSource.regions")
        )
    }

    val jda by lazy {
        JDABuilder.createDefault(config.getString("discord.token"))
            .build()
            .awaitReady()
    }

    val notificationChannels: List<TextChannel> by lazy {
        jda.getTextChannelsByName(config.getString("discord.channel"), true)
    }
}
