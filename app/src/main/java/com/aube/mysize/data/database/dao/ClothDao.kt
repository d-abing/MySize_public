package com.aube.mysize.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aube.mysize.data.model.size.ClothEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ClothDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cloth: ClothEntity)

    @Query("SELECT * FROM cloth ORDER BY date DESC")
    fun getAll(): Flow<List<ClothEntity>>

    @Query("SELECT * FROM cloth WHERE id = :id")
    suspend fun getById(id: Int): ClothEntity?

    @Delete
    suspend fun delete(cloth: ClothEntity)
}
