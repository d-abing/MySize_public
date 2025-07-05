package com.aube.mysize.data.model.dto.recommend

import androidx.annotation.Keep
import com.aube.mysize.domain.model.recommend.RecommendedShop
import com.aube.mysize.presentation.model.recommend.AgeGroup
import com.aube.mysize.presentation.model.recommend.BodyType
import com.aube.mysize.presentation.model.recommend.Gender
import com.aube.mysize.presentation.model.recommend.PriceRange
import com.aube.mysize.presentation.model.recommend.Style

@Keep
data class RecommendedShopDto(
    val shopName: String = "",
    val shopUrl: String = "",
    val styles: List<String> = emptyList(),
    val ageGroup: List<String> = emptyList(),
    val priceRange: String = "",
    val gender: String = "",
    val body: List<String> = emptyList()
)

inline fun <reified T : Enum<T>> String.toEnumOrNull(): T? =
    runCatching { enumValueOf<T>(this.uppercase()) }.getOrNull()

fun RecommendedShopDto.toDomain(): RecommendedShop {
    return RecommendedShop(
        shopName = shopName,
        shopUrl = shopUrl,
        styles = styles.mapNotNull { it.toEnumOrNull<Style>() },
        ageGroup = ageGroup.mapNotNull { it.toEnumOrNull<AgeGroup>() },
        priceRange = priceRange.toEnumOrNull<PriceRange>() ?: PriceRange.MEDIUM,
        gender = gender.toEnumOrNull<Gender>() ?: Gender.UNISEX,
        body = body.mapNotNull { it.toEnumOrNull<BodyType>() }
    )
}