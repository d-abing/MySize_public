package com.aube.mysize.domain.repository

import com.aube.mysize.domain.model.Clothes
import kotlinx.coroutines.flow.Flow

interface ClothesRepository {
    suspend fun insert(clothes: Clothes)
    fun getAll(): Flow<List<Clothes>>
    suspend fun getById(id: Int): Clothes?
    suspend fun delete(clothes: Clothes)
}
