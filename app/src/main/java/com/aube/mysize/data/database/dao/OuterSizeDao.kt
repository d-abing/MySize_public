package com.aube.mysize.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import com.aube.mysize.data.model.size.OuterSizeEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface OuterSizeDao : BaseDao<OuterSizeEntity> {
    @Query("SELECT * FROM outer_size ORDER BY id DESC")
    override fun getAll(): Flow<List<OuterSizeEntity>>

    @Delete
    override suspend fun delete(item: OuterSizeEntity)
}
