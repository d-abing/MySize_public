package com.aube.mysize.domain.repository

import kotlinx.coroutines.flow.Flow

interface BrandRepository {
    suspend fun insert(name: String, category: String)
    fun getAll(): Flow<List<String>>
    fun getByCategory(category: String): Flow<List<String>>
    suspend fun delete(name: String)
}