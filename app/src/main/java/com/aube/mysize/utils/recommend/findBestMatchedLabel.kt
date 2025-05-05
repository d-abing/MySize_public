package com.aube.mysize.utils.recommend

import kotlin.math.absoluteValue

fun findBestMatchedLabel(
    recommendedMeasurements: Map<String, String>,
    extractedSizeMap: Map<String, Map<String, String>>
): String? {
    if (extractedSizeMap.isEmpty()) return null

    return extractedSizeMap.minByOrNull { (_, fields) ->
        recommendedMeasurements.mapNotNull { (key, recommendedValue) ->
            val value = recommendedValue.toFloat()
            fields[key]?.toFloatOrNull()?.let { extractedValue ->
                (value - extractedValue).absoluteValue
            }
        }.averageOrNull() ?: Double.MAX_VALUE
    }?.key
}

fun List<Float>.averageOrNull(): Double? = if (isNotEmpty()) average() else null
