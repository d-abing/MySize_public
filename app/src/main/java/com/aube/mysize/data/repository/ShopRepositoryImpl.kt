package com.aube.mysize.data.repository

import com.aube.mysize.data.database.dao.ShopDao
import com.aube.mysize.data.datastore.SettingsDataStore
import com.aube.mysize.data.model.dto.recommend.RecommendedShopDto
import com.aube.mysize.data.model.dto.recommend.toDomain
import com.aube.mysize.data.model.entity.recommend.toDomain
import com.aube.mysize.data.model.entity.recommend.toEntity
import com.aube.mysize.domain.model.recommend.RecommendedShop
import com.aube.mysize.domain.repository.ShopRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class ShopRepositoryImpl(
    private val firestore: FirebaseFirestore,
    private val dao: ShopDao,
    private val settingsDataStore: SettingsDataStore
) : ShopRepository {
    override suspend fun getRecommendedShops(forceRefresh: Boolean): List<RecommendedShop> {
        val shouldFetch = forceRefresh || settingsDataStore.shouldRefreshRecommendedShops()

        if (!shouldFetch) {
            val cached = dao.getAll()
            if (cached.isNotEmpty()) {
                return cached.map { it.toDomain() }
            }
        }

        val snapshot = firestore.collection("shops").get().await()
        val shops = snapshot.documents.mapNotNull {
            it.toObject(RecommendedShopDto::class.java)?.toDomain()
        }

        dao.clearAll()
        dao.insertAll(shops.map { it.toEntity() })

        settingsDataStore.saveLastShopSyncTime()

        return shops
    }
}
