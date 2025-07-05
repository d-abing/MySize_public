package com.aube.mysize.domain.model.recommend

import com.aube.mysize.presentation.model.recommend.AgeGroup
import com.aube.mysize.presentation.model.recommend.BodyType
import com.aube.mysize.presentation.model.recommend.Gender
import com.aube.mysize.presentation.model.recommend.PriceRange
import com.aube.mysize.presentation.model.recommend.Style

data class RecommendedShop(
    val shopName: String,
    val shopUrl: String,
    val styles: List<Style>,
    val ageGroup: List<AgeGroup>,
    val priceRange: PriceRange,
    val gender: Gender,
    val body: List<BodyType>
)