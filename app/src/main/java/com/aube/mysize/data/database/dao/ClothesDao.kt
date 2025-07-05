package com.aube.mysize.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.aube.mysize.data.model.entity.clothes.ClothesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ClothesDao {
    @Query("SELECT * FROM clothes ORDER BY createdAt DESC")
    fun getAll(): Flow<List<ClothesEntity>>

    @Query("SELECT * FROM clothes")
    suspend fun getAllOnce(): List<ClothesEntity>

    @Query("SELECT * FROM clothes WHERE id = :id")
    suspend fun getById(id: String): ClothesEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(clothes: ClothesEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(clothes: List<ClothesEntity>)

    @Delete
    suspend fun delete(clothes: ClothesEntity)

    @Transaction
    suspend fun replaceAll(newList: List<ClothesEntity>) {
        val currentList = getAllOnce()

        val toDelete = currentList.filterNot { newList.contains(it) }
        val toInsertOrUpdate = newList.filterNot { currentList.contains(it) }

        toDelete.forEach { delete(it) }

        if (toInsertOrUpdate.isNotEmpty()) {
            insertAll(toInsertOrUpdate)
        }
    }
}
