package com.aube.mysize.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.aube.mysize.data.model.BodyProfileEntity

const val DATABASE_VERSION = 1

@Database(
    entities = [BodyProfileEntity::class],
    version = DATABASE_VERSION
)

@TypeConverters(LocalDateConverter::class)
abstract class MySizeDatabase : RoomDatabase() {
    abstract fun bodyProfileDao(): BodyProfileDao
}