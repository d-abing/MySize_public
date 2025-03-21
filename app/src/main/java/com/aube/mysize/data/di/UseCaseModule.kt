package com.aube.mysize.data.di

import com.aube.mysize.domain.repository.BodyProfileRepository
import com.aube.mysize.domain.usecase.GetBodyProfilesUseCase
import com.aube.mysize.domain.usecase.InsertBodyProfileUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {
    @Provides
    fun provideGetBodyProfilesUseCase(
        bodyProfileRepository: BodyProfileRepository
    ): GetBodyProfilesUseCase {
        return GetBodyProfilesUseCase(bodyProfileRepository)
    }
    @Provides
    fun provideInsertBodyProfileUseCase(
        bodyProfileRepository: BodyProfileRepository
    ): InsertBodyProfileUseCase {
        return InsertBodyProfileUseCase(bodyProfileRepository)
    }
}