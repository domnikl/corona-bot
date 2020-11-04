package dev.domnikl.corona

class ChatMessageBuilder {
    fun build(report: CoronaReport): String {
        return """
            :chart_with_upwards_trend:  ${trafficLights(report)} Das ist der Corona-Report für heute (${report.created}) in **${report.region}**:
            **${"%.2f".format(report.sevenDaysIncidence)}** 7-Tage-Inzidenz
            **${report.totalCases}** aktuell Infizierte
            **${report.deathCases}** Todesfälle
        """.trimIndent()
    }

    private fun trafficLights(report: CoronaReport) = when {
        report.sevenDaysIncidence >= 100 -> ":red_circle:".repeat(3)
        report.sevenDaysIncidence >= 50 -> ":black_circle::black_circle::red_circle:"
        report.sevenDaysIncidence >= 35 -> ":black_circle::yellow_circle::black_circle:"
        else -> ":green_circle::black_circle::black_circle:"
    }
}
