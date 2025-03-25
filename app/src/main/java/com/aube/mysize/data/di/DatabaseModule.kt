package com.aube.mysize.data.di

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.aube.mysize.data.database.MySizeDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL("")
        }
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): MySizeDatabase {
        return Room.databaseBuilder(
            appContext.applicationContext,
            MySizeDatabase::class.java,
            "my_size_database"
        )
//            .addMigrations(MIGRATION_1_2)
            .build()
    }
}