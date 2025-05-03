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

sealed class RecommendedShopResult {
    data class Success(
        val recommendedShops: List<RecommendedShop>
    ) : RecommendedShopResult()

    data class Failure(val message: String) : RecommendedShopResult()
}

data class RecommendedShop(
    val shopName: String,
    val shopUrl: String,
    val styles: List<Style>,
    val ageGroup: List<AgeGroup>,
    val priceRange: PriceRange,
    val gender: Gender,
    val body: List<BodyType>
)