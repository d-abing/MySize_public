package com.aube.mysize.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aube.mysize.data.model.size.BodySizeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BodySizeDao : BaseDao<BodySizeEntity>  {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun insert(item: BodySizeEntity)

    @Query("SELECT * FROM body_size ORDER BY id DESC")
    override fun getAll(): Flow<List<BodySizeEntity>>

    @Delete
    override suspend fun delete(item: BodySizeEntity)
}
