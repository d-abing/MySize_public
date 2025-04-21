package com.aube.mysize.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aube.mysize.data.model.clothes.ClothesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ClothesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(clothes: ClothesEntity)

    @Query("SELECT * FROM Clothes ORDER BY createdAt DESC")
    fun getAll(): Flow<List<ClothesEntity>>

    @Query("SELECT * FROM Clothes WHERE id = :id")
    suspend fun getById(id: Int): ClothesEntity?

    @Delete
    suspend fun delete(clothes: ClothesEntity)
}
