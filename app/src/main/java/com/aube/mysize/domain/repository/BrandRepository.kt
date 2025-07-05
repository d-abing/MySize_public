package com.aube.mysize.domain.repository

import com.aube.mysize.domain.model.brand.Brand
import kotlinx.coroutines.flow.Flow

interface BrandRepository {
    suspend fun insert(brand: Brand)
    fun getAll(): Flow<List<Brand>>
    fun getByCategory(category: String): Flow<List<Brand>>
    suspend fun delete(brand: Brand)
}