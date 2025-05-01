package com.aube.mysize.presentation.model

sealed class RecommendedSizeResult {
    data class Success(
        val category: String,
        val recommendedList: Map<String, String>
    ) : RecommendedSizeResult()

    data class NoData(
        val category: String
    ) : RecommendedSizeResult()
}