package com.aube.mysize.presentation.model.recommend

import com.aube.mysize.domain.model.recommend.RecommendedShop
import com.aube.mysize.domain.model.size.SizeCategory

sealed class RecommendedSizeResult {
    data class Success(
        val category: SizeCategory,
        val typeToSizeMap: Map<String, SizeDetail>,
        val mostSelectedLabel: Map<String, String> = emptyMap()
    ): RecommendedSizeResult()

    data class Failure(val message: String): RecommendedSizeResult()
}


data class SizeDetail(
    val measurements: Map<String, String>
)

sealed class RecommendedShopResult {
    object Loading : RecommendedShopResult()
    data class Success(
        val recommendedShops: List<RecommendedShop>
    ) : RecommendedShopResult()

    data class Failure(val message: String) : RecommendedShopResult()
}