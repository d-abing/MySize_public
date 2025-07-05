package com.aube.mysize.utils.color_anlysis

import com.aube.mysize.presentation.model.clothes.Detail
import com.aube.mysize.presentation.model.clothes.PersonalToneAnalysisResult
import com.aube.mysize.presentation.model.clothes.Season
import com.aube.mysize.presentation.model.clothes.Temperature
import com.aube.mysize.presentation.model.clothes.ToneCombination
import kotlin.math.pow
import kotlin.math.sqrt

fun analyzePersonalTone(colors: List<Int>): PersonalToneAnalysisResult {
    require(colors.isNotEmpty()) { "Color list cannot be empty" }

    val hsvList = colors.map { color ->
        val hsv = FloatArray(3)
        android.graphics.Color.colorToHSV(color, hsv)
        hsv
    }

    val temperatureBuckets = mutableMapOf(
        Temperature.WARM to 0,
        Temperature.COOL to 0
    )
    val seasonBuckets = mutableMapOf(
        Season.SPRING to 0,
        Season.SUMMER to 0,
        Season.AUTUMN to 0,
        Season.WINTER to 0
    )
    val detailBuckets = mutableMapOf(
        Detail.VIVID to 0,
        Detail.MUTED to 0,
        Detail.DEEP to 0,
        Detail.SOFT to 0
    )

    val size = colors.size.toFloat()

    hsvList.forEach { hsv ->
        val (h, s, v) = hsv
        val temp = if (h < 180 || h > 330) Temperature.WARM else Temperature.COOL
        temperatureBuckets[temp] = temperatureBuckets[temp]!! + 1

        val season = when {
            temp == Temperature.WARM && v >= 0.7f && s >= 0.5f -> Season.SPRING
            temp == Temperature.WARM -> Season.AUTUMN
            v >= 0.7f && s <= 0.5f -> Season.SUMMER
            else -> Season.WINTER
        }
        seasonBuckets[season] = seasonBuckets[season]!! + 1

        val detail = when {
            s >= 0.7f && v >= 0.7f -> Detail.VIVID
            s < 0.4f -> Detail.MUTED
            v < 0.5f -> Detail.DEEP
            else -> Detail.SOFT
        }
        detailBuckets[detail] = detailBuckets[detail]!! + 1
    }

    val avgHsv = FloatArray(3).apply {
        this[0] = hsvList.map { it[0] }.average().toFloat()
        this[1] = hsvList.map { it[1] }.average().toFloat()
        this[2] = hsvList.map { it[2] }.average().toFloat()
    }

    val bestMatchedColors = colors.zip(hsvList)
        .sortedBy { hsv -> euclideanDistance(hsv.second, avgHsv) }
        .take(maxOf(1, (colors.size * 0.2).toInt()))
        .map { it.first }

    fun <T> normalize(bucket: Map<T, Int>) = bucket.mapValues { it.value / size }

    val mainTone = ToneCombination(
        temperatureBuckets.maxByOrNull { it.value }!!.key,
        seasonBuckets.maxByOrNull { it.value }!!.key,
        detailBuckets.maxByOrNull { it.value }!!.key
    )

    val recommendedColors = recommendedPaletteMap[mainTone] ?: emptyList()

    return PersonalToneAnalysisResult(
        temperatureRatio = normalize(temperatureBuckets),
        seasonRatio = normalize(seasonBuckets),
        detailRatio = normalize(detailBuckets),
        bestMatchedColors = bestMatchedColors,
        recommendedColors = recommendedColors,
        mainTone = mainTone
    )
}

fun euclideanDistance(hsv1: FloatArray, hsv2: FloatArray): Double {
    return sqrt(
        (hsv1[0] - hsv2[0]).toDouble().pow(2.0) +
                (hsv1[1] - hsv2[1]).toDouble().pow(2.0) +
                (hsv1[2] - hsv2[2]).toDouble().pow(2.0)
    )
}