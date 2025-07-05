package com.aube.mysize.domain.repository

import com.aube.mysize.domain.model.size.Size
import com.aube.mysize.domain.model.size.SizeCategory
import kotlinx.coroutines.flow.Flow

interface SizeRepository {
    suspend fun insert(category: SizeCategory, size: Size): String
    suspend fun delete(category: SizeCategory, size: Size, clothesIds: Set<String>?)
    fun getAll(uid: String?): Flow<Map<SizeCategory, List<Size>>>
    fun getSizeById(category: SizeCategory, sizeId: String, ownerUid: String): Flow<Size?>
}