package dev.domnikl.corona

import java.time.LocalDate

interface DataSource {
    fun get(): List<CoronaReport>
}

data class CoronaReport(
    val created: LocalDate,
    val region: String,
    val sevenDaysIncidence: Double,
    val totalCases: Int,
    val deathCases: Int,
)
