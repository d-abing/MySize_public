package com.aube.mysize.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aube.mysize.data.model.size.AccessorySizeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AccessorySizeDao: BaseDao<AccessorySizeEntity> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun insert(item: AccessorySizeEntity)

    @Query("SELECT * FROM accessory_size ORDER BY date DESC")
    override fun getAll(): Flow<List<AccessorySizeEntity>>

    @Delete
    override suspend fun delete(item: AccessorySizeEntity)
}
