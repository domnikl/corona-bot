package dev.domnikl.corona

import com.fasterxml.jackson.annotation.JsonAlias
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.OkHttpClient
import okhttp3.Request
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
        val arcgis = objectMapper.readValue(response.body?.byteStream(), Arcgis::class.java)

        val dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        val sortIndexes = regions.withIndex().associate { it.value to it.index }

        return arcgis.features
            .filter { it.attributes.gen in regions }
            .sortedBy { sortIndexes[it.attributes.gen] }
            .map { CoronaReport(
                LocalDate.from(
                    dateTimeFormatter.parse(
                        it.attributes.lastUpdate,
                        ParsePosition(0)
                    )
                ),
                it.attributes.county,
                it.attributes.cases7Per100K,
                it.attributes.cases,
                it.attributes.deaths
            ) }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class Attributes(
        @JsonAlias("GEN")
        val gen: String,
        @JsonAlias("last_update")
        val lastUpdate: String,
        val county: String,
        @JsonAlias("cases7_per_100k")
        val cases7Per100K: Double,
        val cases: Int,
        val deaths: Int
    )

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class Features(val attributes: Attributes)

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class Arcgis(val features: List<Features>)
}
