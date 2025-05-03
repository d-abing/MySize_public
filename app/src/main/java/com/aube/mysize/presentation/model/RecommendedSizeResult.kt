package com.aube.mysize.presentation.model

sealed class RecommendedSizeResult {

    data class Success(
        val category: SizeCategory,
        val typeToSizeMap: Map<String, SizeDetail>,
        val mostSelectedLabel: Map<String, String> = emptyMap()
    ): RecommendedSizeResult()

    data class Failure(val message: String): RecommendedSizeResult()
}


data class SizeDetail(
    val measurements: Map<String, Float>
)