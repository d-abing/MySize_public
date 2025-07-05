package com.aube.mysize.data.di

import android.content.Context
import androidx.room.Room
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
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): MySizeDatabase {
        return Room.databaseBuilder(
            appContext.applicationContext,
            MySizeDatabase::class.java,
            "my_size_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}