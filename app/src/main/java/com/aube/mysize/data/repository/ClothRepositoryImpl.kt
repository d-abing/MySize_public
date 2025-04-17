package com.aube.mysize.data.repository

import com.aube.mysize.data.database.dao.ClothDao
import com.aube.mysize.data.model.size.toDomain
import com.aube.mysize.domain.model.Cloth
import com.aube.mysize.domain.model.toEntity
import com.aube.mysize.domain.repository.ClothRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ClothRepositoryImpl(
    private val dao: ClothDao
) : ClothRepository {
    override suspend fun insert(cloth: Cloth) {
        dao.insert(cloth.toEntity())
    }

    override fun getAll(): Flow<List<Cloth>> {
        return dao.getAll().map { list -> list.map { it.toDomain() } }
    }

    override suspend fun getById(id: Int): Cloth? {
        return dao.getById(id)?.toDomain()
    }

    override suspend fun delete(cloth: Cloth) {
        dao.delete(cloth.toEntity())
    }
}
