package com.aube.mysize.domain.repository

import kotlinx.coroutines.flow.Flow

interface SizeRepository<T> {
    suspend fun insert(item: T): Long
    fun getAll(): Flow<List<T>>
    suspend fun delete(item: T)
}