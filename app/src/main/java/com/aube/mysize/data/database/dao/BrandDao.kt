package com.aube.mysize.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aube.mysize.data.model.brand.BrandEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BrandDao {
    @Query("SELECT * FROM brand ORDER BY name")
    fun getAll(): Flow<List<BrandEntity>>

    @Query("SELECT * FROM brand WHERE category = :category ORDER BY name")
    fun getByCategory(category: String): Flow<List<BrandEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(brand: BrandEntity)

    @Query("DELETE FROM brand WHERE name = :name")
    suspend fun delete(name: String)
}