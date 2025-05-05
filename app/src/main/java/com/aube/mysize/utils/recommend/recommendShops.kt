package com.aube.mysize.utils.recommend

import com.aube.mysize.domain.model.size.BodySize
import com.aube.mysize.presentation.model.BodyType
import com.aube.mysize.presentation.model.Gender
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
        val styleMatch = shop.styles.any { it in userPreference.styles || it == Style.ALL }
        val ageMatch = shop.ageGroup.any { it in userPreference.ageGroups }
        val priceMatch = shop.priceRange in userPreference.priceRanges

        val bodyMatch = shop.body.any { bodyType ->
            height in bodyType.heightRange && weight in bodyType.weightRange
        } || shop.body.contains(BodyType.ALL)

        val genderMatch = shop.gender == userPreference.gender || shop.gender == Gender.UNISEX

        styleMatch && ageMatch && priceMatch && bodyMatch && genderMatch
    }

    return if (matchedShops.isNotEmpty()) {
        RecommendedShopResult.Success(matchedShops)
    } else {
        RecommendedShopResult.Failure("조건에 맞는 쇼핑몰이 없습니다")
    }
}
