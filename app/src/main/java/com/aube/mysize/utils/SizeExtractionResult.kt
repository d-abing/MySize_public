package com.aube.mysize.utils

sealed class SizeExtractionResult {
    data class Success(
        val sizeLabels: List<String>,
        val sizeMap: Map<String, Map<String, String>>
    ) : SizeExtractionResult()

    object Incomplete : SizeExtractionResult()
    object Failed : SizeExtractionResult()
}