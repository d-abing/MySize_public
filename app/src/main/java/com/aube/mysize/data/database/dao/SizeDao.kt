package com.aube.mysize.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aube.mysize.data.model.entity.size.AccessorySizeEntity
import com.aube.mysize.data.model.entity.size.BodySizeEntity
import com.aube.mysize.data.model.entity.size.BottomSizeEntity
import com.aube.mysize.data.model.entity.size.OnePieceSizeEntity
import com.aube.mysize.data.model.entity.size.OuterSizeEntity
import com.aube.mysize.data.model.entity.size.ShoeSizeEntity
import com.aube.mysize.data.model.entity.size.TopSizeEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

abstract class SizeDao<T> {
    abstract fun getAll(): Flow<List<T>>

    private suspend fun getAllOnce(): List<T> {
        return getAll().first()
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(item: T): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAll(items: List<T>)

    @Delete
    abstract suspend fun delete(item: T)

    suspend fun replaceAll(newItems: List<T>) {
        val currentItems = getAllOnce()

        val itemsToDelete = currentItems.filterNot { newItems.contains(it) }
        val itemsToInsertOrUpdate = newItems.filterNot { currentItems.contains(it) }

        if (itemsToDelete.isNotEmpty()) {
            itemsToDelete.forEach { delete(it) }
        }

        if (itemsToInsertOrUpdate.isNotEmpty()) {
            insertAll(itemsToInsertOrUpdate)
        }
    }
}

@Dao
abstract class BodySizeDao : SizeDao<BodySizeEntity>() {
    @Query("SELECT * FROM body_size ORDER BY date DESC")
    abstract override fun getAll(): Flow<List<BodySizeEntity>>

    @Delete
    abstract override suspend fun delete(item: BodySizeEntity)
}

@Dao
abstract class TopSizeDao : SizeDao<TopSizeEntity>() {
    @Query("SELECT * FROM top_size ORDER BY date DESC")
    abstract override fun getAll(): Flow<List<TopSizeEntity>>

    @Delete
    abstract override suspend fun delete(item: TopSizeEntity)
}

@Dao
abstract class BottomSizeDao : SizeDao<BottomSizeEntity>() {
    @Query("SELECT * FROM bottom_size ORDER BY date DESC")
    abstract override fun getAll(): Flow<List<BottomSizeEntity>>

    @Delete
    abstract override suspend fun delete(item: BottomSizeEntity)
}

@Dao
abstract class OuterSizeDao : SizeDao<OuterSizeEntity>() {
    @Query("SELECT * FROM outer_size ORDER BY date DESC")
    abstract override fun getAll(): Flow<List<OuterSizeEntity>>

    @Delete
    abstract override suspend fun delete(item: OuterSizeEntity)
}


@Dao
abstract class OnePieceSizeDao : SizeDao<OnePieceSizeEntity>() {
    @Query("SELECT * FROM one_piece_size ORDER BY date DESC")
    abstract override fun getAll(): Flow<List<OnePieceSizeEntity>>

    @Delete
    abstract override suspend fun delete(item: OnePieceSizeEntity)
}

@Dao
abstract class ShoeSizeDao : SizeDao<ShoeSizeEntity>() {
    @Query("SELECT * FROM shoe_size ORDER BY date DESC")
    abstract override fun getAll(): Flow<List<ShoeSizeEntity>>

    @Delete
    abstract override suspend fun delete(item: ShoeSizeEntity)
}


@Dao
abstract class AccessorySizeDao: SizeDao<AccessorySizeEntity>() {
    @Query("SELECT * FROM accessory_size ORDER BY date DESC")
    abstract override fun getAll(): Flow<List<AccessorySizeEntity>>

    @Delete
    abstract override suspend fun delete(item: AccessorySizeEntity)
}
