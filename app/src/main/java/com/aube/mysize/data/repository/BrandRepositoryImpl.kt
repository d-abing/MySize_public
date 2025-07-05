package com.aube.mysize.data.repository

import com.aube.mysize.data.database.dao.BrandDao
import com.aube.mysize.data.model.entity.brand.toDomain
import com.aube.mysize.data.model.entity.brand.toEntity
import com.aube.mysize.domain.model.brand.Brand
import com.aube.mysize.domain.repository.BrandRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class BrandRepositoryImpl(
    private val dao: BrandDao
) : BrandRepository {

    override fun getAll(): Flow<List<Brand>> =
        dao.getAll().map { list -> list.map { it.toDomain()} }

    override suspend fun insert(brand: Brand) {
        dao.insert(brand.toEntity())
    }

    override fun getByCategory(category: String): Flow<List<Brand>> =
        dao.getByCategory(category).map { list -> list.map { it.toDomain() } }

    override suspend fun delete(brand: Brand) {
        dao.delete(brand.toEntity())
    }
}