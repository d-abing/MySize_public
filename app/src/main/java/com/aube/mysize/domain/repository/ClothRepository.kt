package com.aube.mysize.domain.repository

import com.aube.mysize.domain.model.Cloth
import kotlinx.coroutines.flow.Flow

interface ClothRepository {
    suspend fun insert(cloth: Cloth)
    fun getAll(): Flow<List<Cloth>>
    suspend fun getById(id: Int): Cloth?
    suspend fun delete(cloth: Cloth)
}
