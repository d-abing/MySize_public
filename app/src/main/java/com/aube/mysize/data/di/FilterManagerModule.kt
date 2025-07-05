package com.aube.mysize.data.di

import com.aube.mysize.domain.repository.BlockedUserRepository
import com.aube.mysize.domain.repository.ReportRepository
import com.aube.mysize.domain.usecase.blocking.CheckIfBlockedByUserUseCase
import com.aube.mysize.domain.usecase.blocking.DeleteBlockedUserUseCase
import com.aube.mysize.domain.usecase.blocking.GetBlockedMeUsersUseCase
import com.aube.mysize.domain.usecase.blocking.GetBlockedUsersUseCase
import com.aube.mysize.domain.usecase.blocking.InsertBlockedUserUseCase
import com.aube.mysize.domain.usecase.report.GetReportedClothesUseCase
import com.aube.mysize.domain.usecase.report.SubmitReportUseCase
import com.aube.mysize.domain.usecases.BlockingUseCases
import com.aube.mysize.domain.usecases.ReportUseCases
import com.aube.mysize.presentation.viewmodel.clothes.ClothesFilterManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FilterManagerModule {

    @Provides
    @Singleton
    fun provideClothesFilterManager(
        reportUseCases: ReportUseCases,
        blockingUseCases: BlockingUseCases
    ): ClothesFilterManager {
        return ClothesFilterManager(reportUseCases, blockingUseCases)
    }

    @Provides
    @Singleton
    fun provideReportUseCases(
        submitReport: SubmitReportUseCase,
        getReportedClothes: GetReportedClothesUseCase
    ) = ReportUseCases(submitReport, getReportedClothes)

    @Provides
    @Singleton
    fun provideBlockingUseCases(
        insert: InsertBlockedUserUseCase,
        delete: DeleteBlockedUserUseCase,
        getBlocked: GetBlockedUsersUseCase,
        getBlockedMe: GetBlockedMeUsersUseCase,
        check: CheckIfBlockedByUserUseCase
    ) = BlockingUseCases(insert, delete, getBlocked, getBlockedMe, check)

    @Provides @Singleton
    fun provideGetReportedClothesUseCase(
        repository: ReportRepository
    ) = GetReportedClothesUseCase(repository)

    @Provides @Singleton
    fun provideSubmitReportUseCase(
        repository: ReportRepository
    ) = SubmitReportUseCase(repository)

    @Provides @Singleton
    fun provideGetBlockedUsersUseCase(
        repository: BlockedUserRepository
    ) = GetBlockedUsersUseCase(repository)

    @Provides @Singleton
    fun provideGetBlockedMeUsersUseCase(
        repository: BlockedUserRepository
    ) = GetBlockedMeUsersUseCase(repository)

    @Provides @Singleton
    fun provideInsertBlockedUserUseCase(
        repository: BlockedUserRepository
    ) = InsertBlockedUserUseCase(repository)

    @Provides @Singleton
    fun provideDeleteBlockedUserUseCase(
        repository: BlockedUserRepository
    ) = DeleteBlockedUserUseCase(repository)

    @Provides @Singleton
    fun provideCheckIfBlockedByUserUseCase(
        repository: BlockedUserRepository
    ) = CheckIfBlockedByUserUseCase(repository)
}
