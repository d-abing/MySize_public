package com.aube.mysize.utils.recommend

import com.aube.mysize.domain.model.size.BodySize
import com.aube.mysize.presentation.model.BodyType
import com.aube.mysize.presentation.model.RecommendedShopResult
import com.aube.mysize.presentation.model.Style
import com.aube.mysize.presentation.model.UserPreference
import knownShops

fun recommendShops(
    bodySize: BodySize,
    userPreference: UserPreference,
): RecommendedShopResult {

    val height = bodySize.height.toInt()
    val weight = bodySize.weight.toInt()

    val matchedShops = knownShops.filter { shop ->
        // 1. 스타일/가격대/나이대 필터
        val styleMatch = shop.styles.any { it in userPreference.styles || it == Style.ALL }
        val ageMatch = userPreference.ageGroup in shop.ageGroup
        val priceMatch = shop.priceRange == userPreference.priceRange

        val bodyMatch = shop.body.any { bodyType ->
            height in bodyType.heightRange && weight in bodyType.weightRange
        } || shop.body.contains(BodyType.ALL)

        styleMatch && ageMatch && priceMatch && bodyMatch
    }

    return if (matchedShops.isNotEmpty()) {
        RecommendedShopResult.Success(matchedShops)
    } else {
        RecommendedShopResult.Failure("조건에 맞는 쇼핑몰이 없습니다.")
    }
}
