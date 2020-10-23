package dev.domnikl.corona

import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.OkHttpClient
import okhttp3.Request
import org.geojson.FeatureCollection
import java.net.URL
import java.text.ParsePosition
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class RobertKochDataSource(
    private val client: OkHttpClient,
    private val objectMapper: ObjectMapper,
    private val url: URL,
    private val regions: List<String>
) : DataSource {
    override fun get(): List<CoronaReport> {
        val request = Request.Builder()
            .url(url)
            .build()

        val response = client.newCall(request).execute()

        val featureCollection: FeatureCollection = objectMapper.readValue(
            response.body?.byteStream(),
            FeatureCollection::class.java
        )

        val dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        val sortIndexes = regions.withIndex().associate { it.value to it.index }

        return featureCollection.features
            .filter { it.properties["GEN"] in regions }
            .sortedBy { sortIndexes[it.properties["GEN"]] }
            .map { CoronaReport(
                LocalDate.from(
                    dateTimeFormatter.parse(
                        it.properties["last_update"] as String,
                        ParsePosition(0)
                    )
                ),
                it.properties["county"] as String,
                it.properties["cases7_per_100k"] as Double,
                it.properties["cases"] as Int,
                it.properties["deaths"] as Int
            ) }
    }
}
