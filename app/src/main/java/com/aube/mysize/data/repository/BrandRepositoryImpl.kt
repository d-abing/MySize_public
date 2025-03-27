package com.aube.mysize.data.repository

import com.aube.mysize.data.database.dao.BrandDao
import com.aube.mysize.data.model.size.BrandEntity
import com.aube.mysize.domain.repository.BrandRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BrandRepositoryImpl(
    private val dao: BrandDao
) : BrandRepository {
    override fun getAll(): Flow<List<String>> =
        dao.getAll().map { list -> list.map { it.name } }

    override suspend fun insert(name: String, category: String) {
        dao.insert(BrandEntity(name = name, category = category))
    }

    override fun getByCategory(category: String): Flow<List<String>> =
        dao.getByCategory(category).map { list -> list.map { it.name } }

    override suspend fun delete(name: String) {
        dao.delete(name)
    }
}
