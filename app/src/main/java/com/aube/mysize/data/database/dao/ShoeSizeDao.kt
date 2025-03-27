package com.aube.mysize.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aube.mysize.data.model.size.ShoeSizeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoeSizeDao : BaseDao<ShoeSizeEntity> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun insert(item: ShoeSizeEntity)

    @Query("SELECT * FROM shoe_size ORDER BY id DESC")
    override fun getAll(): Flow<List<ShoeSizeEntity>>

    @Delete
    override suspend fun delete(item: ShoeSizeEntity)
}
