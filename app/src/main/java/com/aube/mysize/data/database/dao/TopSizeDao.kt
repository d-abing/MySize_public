package com.aube.mysize.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import com.aube.mysize.data.model.size.TopSizeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TopSizeDao : BaseDao<TopSizeEntity> {
    @Query("SELECT * FROM top_size ORDER BY id DESC")
    override fun getAll(): Flow<List<TopSizeEntity>>

    @Delete
    override suspend fun delete(item: TopSizeEntity)
}
