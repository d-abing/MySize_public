package com.aube.mysize.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aube.mysize.data.model.entity.recommend.RecommendedShopEntity

@Dao
interface ShopDao {
    @Query("SELECT * FROM shops")
    suspend fun getAll(): List<RecommendedShopEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(shops: List<RecommendedShopEntity>)

    @Query("DELETE FROM shops")
    suspend fun clearAll()
}
