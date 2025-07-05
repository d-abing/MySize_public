package com.aube.mysize.domain.repository

import com.aube.mysize.domain.model.clothes.Clothes
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.flow.Flow

interface ClothesRepository {
    suspend fun insert(clothes: Clothes, imageBytes: ByteArray?, isEdit: Boolean = false): String?
    fun getUserAllClothes(uid: String?): Flow<List<Clothes>>
    suspend fun getUserPublicClothesPaged(
        uid: String,
        pageSize: Int,
        startAfter: DocumentSnapshot? = null
    ): Pair<List<Clothes>, DocumentSnapshot?>
    suspend fun getOtherUsersClothesPaged(
        uid: String,
        pageSize: Int,
        startAfter: DocumentSnapshot? = null
    ): Pair<List<Clothes>, DocumentSnapshot?>
    suspend fun getById(id: String): Clothes?
    suspend fun getClothesByTag(
        tag: String,
        pageSize: Int,
        lastSnapshot: DocumentSnapshot?
    ): Pair<List<Clothes>, DocumentSnapshot?>
    suspend fun delete(clothes: Clothes)
    suspend fun saveRecentView(clothesId: String)
    suspend fun getRecentViews(): List<Clothes>
    suspend fun deleteRecentViews()
}
