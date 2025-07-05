package com.aube.mysize.presentation.model.clothes

import androidx.compose.ui.graphics.Color

data class PersonalToneAnalysisResult(
    val temperatureRatio: Map<Temperature, Float>,
    val seasonRatio: Map<Season, Float>,
    val detailRatio: Map<Detail, Float>,
    val bestMatchedColors: List<Int>,
    val recommendedColors: List<Int>,
    val mainTone: ToneCombination
)

data class ToneCombination(
    val temperature: Temperature,
    val season: Season,
    val detailTone: Detail
) {
    val displayName: String
        get() = "${temperature.toDisplayName()} ${season.toDisplayName()} ${detailTone.toDisplayName()}"
}

enum class Temperature { WARM, COOL;

    companion object {
        val colors: Map<Temperature, Color> = mapOf(
            WARM to Color(0xFFFFC107),
            COOL to Color(0xFF2196F3)
        )
    }
}
enum class Season { SPRING, SUMMER, AUTUMN, WINTER;

    companion object {
        val colors: Map<Season, Color> = mapOf(
            SPRING to Color(0xFFFFAB91),
            SUMMER to Color(0xFF81D4FA),
            AUTUMN to Color(0xFFFF9800),
            WINTER to Color(0xFF7986CB)
        )
    }
}
enum class Detail { VIVID, MUTED, DEEP, SOFT;

    companion object {
        val colors: Map<Detail, Color> = mapOf(
            VIVID to Color(0xFFFF4081),
            MUTED to Color(0xFF9E9E9E),
            DEEP to Color(0xFF6A1B9A),
            SOFT to Color(0xFFCE93D8)
        )
    }
}

fun Temperature.toDisplayName() = when (this) {
    Temperature.WARM -> "웜톤"
    Temperature.COOL -> "쿨톤"
}

fun Season.toDisplayName() = when (this) {
    Season.SPRING -> "봄"
    Season.SUMMER -> "여름"
    Season.AUTUMN -> "가을"
    Season.WINTER -> "겨울"
}

fun Detail.toDisplayName() = when (this) {
    Detail.VIVID -> "Vivid"
    Detail.MUTED -> "Muted"
    Detail.DEEP -> "Deep"
    Detail.SOFT -> "Soft"
}