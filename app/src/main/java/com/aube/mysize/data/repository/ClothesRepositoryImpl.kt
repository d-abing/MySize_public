package com.aube.mysize.data.repository

import com.aube.mysize.data.database.dao.ClothesDao
import com.aube.mysize.data.model.size.toDomain
import com.aube.mysize.domain.model.Clothes
import com.aube.mysize.domain.model.toEntity
import com.aube.mysize.domain.repository.ClothesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ClothesRepositoryImpl(
    private val dao: ClothesDao
) : ClothesRepository {
    override suspend fun insert(clothes: Clothes) {
        dao.insert(clothes.toEntity())
    }

    override fun getAll(): Flow<List<Clothes>> {
        return dao.getAll().map { list -> list.map { it.toDomain() } }
    }

    override suspend fun getById(id: Int): Clothes? {
        return dao.getById(id)?.toDomain()
    }

    override suspend fun delete(clothes: Clothes) {
        dao.delete(clothes.toEntity())
    }
}
