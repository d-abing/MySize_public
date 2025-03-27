package com.aube.mysize.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aube.mysize.data.model.size.OnePieceSizeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface OnePieceSizeDao : BaseDao<OnePieceSizeEntity> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun insert(item: OnePieceSizeEntity)

    @Query("SELECT * FROM one_piece_size ORDER BY id DESC")
    override fun getAll(): Flow<List<OnePieceSizeEntity>>

    @Delete
    override suspend fun delete(item: OnePieceSizeEntity)
}
