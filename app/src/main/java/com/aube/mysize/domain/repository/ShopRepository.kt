package com.aube.mysize.domain.repository

import com.aube.mysize.domain.model.recommend.RecommendedShop

interface ShopRepository {
    suspend fun getRecommendedShops(forceRefresh: Boolean): List<RecommendedShop>
}
