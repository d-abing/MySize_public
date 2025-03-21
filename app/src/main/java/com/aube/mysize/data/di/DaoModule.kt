package com.aube.mysize.data.di

import com.aube.mysize.data.database.BodyProfileDao
import com.aube.mysize.data.database.MySizeDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {

    @Provides
    fun provideBodyProfileDao(database: MySizeDatabase): BodyProfileDao {
        return database.bodyProfileDao()
    }
}