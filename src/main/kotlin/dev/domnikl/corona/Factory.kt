package dev.domnikl.corona

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.typesafe.config.Config
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.TextChannel
import okhttp3.OkHttpClient
import java.net.URL

class Factory(config: Config) {
    val dataSource: DataSource by lazy {
        RobertKochDataSource(
            OkHttpClient(),
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

    val notificationChannel: TextChannel by lazy {
        jda.getTextChannelsByName(config.getString("discord.channel"), true).first()
    }
}
