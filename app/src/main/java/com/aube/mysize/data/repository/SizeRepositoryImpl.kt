package com.aube.mysize.data.repository

import com.aube.mysize.data.database.dao.BaseDao
import com.aube.mysize.domain.repository.SizeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

abstract class SizeRepositoryImpl<T : Any, E : Any>(
    private val dao: BaseDao<E>,
    private val toEntity: (T) -> E,
    private val toDomain: (E) -> T
) : SizeRepository<T> {

    override suspend fun insert(item: T): Long {
        return dao.insert(toEntity(item))
    }

    override fun getAll(): Flow<List<T>> {
        return dao.getAll().map { it.map(toDomain) }
    }

    override suspend fun delete(item: T) {
        dao.delete(toEntity(item))
    }
}
