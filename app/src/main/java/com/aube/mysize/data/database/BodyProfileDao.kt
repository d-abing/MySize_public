package com.aube.mysize.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aube.mysize.data.model.BodyProfileEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BodyProfileDao {
    @Query("SELECT * FROM body_profile ORDER BY date DESC")
    fun getAll(): Flow<List<BodyProfileEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(profile: BodyProfileEntity): Long

    @Delete
    suspend fun delete(profile: BodyProfileEntity)
}