package com.aube.mysize.data.model.entity.recommend

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aube.mysize.data.model.dto.recommend.toEnumOrNull
import com.aube.mysize.domain.model.recommend.RecommendedShop
import com.aube.mysize.presentation.model.recommend.AgeGroup
import com.aube.mysize.presentation.model.recommend.BodyType
import com.aube.mysize.presentation.model.recommend.Gender
import com.aube.mysize.presentation.model.recommend.PriceRange
import com.aube.mysize.presentation.model.recommend.Style

@Entity(tableName = "shops")
data class RecommendedShopEntity(
    @PrimaryKey val shopName: String,
    val shopUrl: String,
    val styles: List<String>,
    val ageGroup: List<String>,
    val priceRange: String,
    val gender: String,
    val body: List<String>
)

fun RecommendedShopEntity.toDomain(): RecommendedShop = RecommendedShop(
    shopName = shopName,
    shopUrl = shopUrl,
    styles = styles.mapNotNull { it.toEnumOrNull<Style>() },
    ageGroup = ageGroup.mapNotNull { it.toEnumOrNull<AgeGroup>() },
    priceRange = priceRange.toEnumOrNull<PriceRange>() ?: PriceRange.MEDIUM,
    gender = gender.toEnumOrNull<Gender>() ?: Gender.UNISEX,
    body = body.mapNotNull { it.toEnumOrNull<BodyType>() }
)

fun RecommendedShop.toEntity(): RecommendedShopEntity = RecommendedShopEntity(
    shopName = shopName,
    shopUrl = shopUrl,
    styles = styles.map { it.name },
    ageGroup = ageGroup.map { it.name },
    priceRange = priceRange.name,
    gender = gender.name,
    body = body.map { it.name }
)