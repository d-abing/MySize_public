package com.aube.mysize.data.di

import com.aube.mysize.data.database.BodyProfileDao
import com.aube.mysize.data.repository.BodyProfileProfileRepositoryImpl
import com.aube.mysize.domain.repository.BodyProfileRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {
    @Provides
    fun provideBodyRepository(bodyProfileDao: BodyProfileDao): BodyProfileRepository {
        return BodyProfileProfileRepositoryImpl(bodyProfileDao)
    }
}