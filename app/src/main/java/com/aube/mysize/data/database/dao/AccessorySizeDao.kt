package com.aube.mysize.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import com.aube.mysize.data.model.size.AccessorySizeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AccessorySizeDao: BaseDao<AccessorySizeEntity> {
    @Query("SELECT * FROM accessory_size ORDER BY id DESC")
    override fun getAll(): Flow<List<AccessorySizeEntity>>

    @Delete
    override suspend fun delete(item: AccessorySizeEntity)
}
