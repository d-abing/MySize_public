package com.aube.mysize.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aube.mysize.data.model.size.BottomSizeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BottomSizeDao : BaseDao<BottomSizeEntity> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun insert(item: BottomSizeEntity)

    @Query("SELECT * FROM bottom_size ORDER BY id DESC")
    override fun getAll(): Flow<List<BottomSizeEntity>>

    @Delete
    override suspend fun delete(item: BottomSizeEntity)
}
