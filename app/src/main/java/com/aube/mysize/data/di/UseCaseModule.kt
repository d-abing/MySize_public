package com.aube.mysize.data.di

import com.aube.mysize.domain.model.AccessorySize
import com.aube.mysize.domain.model.BodySize
import com.aube.mysize.domain.model.BottomSize
import com.aube.mysize.domain.model.OnePieceSize
import com.aube.mysize.domain.model.OuterSize
import com.aube.mysize.domain.model.ShoeSize
import com.aube.mysize.domain.model.TopSize
import com.aube.mysize.domain.repository.BrandRepository
import com.aube.mysize.domain.repository.SizeRepository
import com.aube.mysize.domain.usecase.DeleteBrandUseCase
import com.aube.mysize.domain.usecase.DeleteSizeUseCase
import com.aube.mysize.domain.usecase.GetBrandListByCategoryUseCase
import com.aube.mysize.domain.usecase.GetBrandListUseCase
import com.aube.mysize.domain.usecase.GetSizeListUseCase
import com.aube.mysize.domain.usecase.InsertBrandUseCase
import com.aube.mysize.domain.usecase.InsertSizeUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    // ───── 신체 사이즈 ─────
    @Provides
    fun provideInsertBodySizeUseCase(
        repository: SizeRepository<BodySize>
    ): InsertSizeUseCase<BodySize> = InsertSizeUseCase(repository)

    @Provides
    fun provideGetBodySizeListUseCase(
        repository: SizeRepository<BodySize>
    ): GetSizeListUseCase<BodySize> = GetSizeListUseCase(repository)

    @Provides
    fun provideDeleteBodySizeUseCase(
        repository: SizeRepository<BodySize>
    ): DeleteSizeUseCase<BodySize> = DeleteSizeUseCase(repository)

    // ───── 상의 사이즈 ─────
    @Provides
    fun provideInsertTopSizeUseCase(
        repository: SizeRepository<TopSize>
    ): InsertSizeUseCase<TopSize> = InsertSizeUseCase(repository)

    @Provides
    fun provideGetTopSizeListUseCase(
        repository: SizeRepository<TopSize>
    ): GetSizeListUseCase<TopSize> = GetSizeListUseCase(repository)

    @Provides
    fun provideDeleteTopSizeUseCase(
        repository: SizeRepository<TopSize>
    ): DeleteSizeUseCase<TopSize> = DeleteSizeUseCase(repository)

    // ───── 하의 사이즈 ─────
    @Provides
    fun provideInsertBottomSizeUseCase(
        repository: SizeRepository<BottomSize>
    ): InsertSizeUseCase<BottomSize> = InsertSizeUseCase(repository)

    @Provides
    fun provideGetBottomSizeListUseCase(
        repository: SizeRepository<BottomSize>
    ): GetSizeListUseCase<BottomSize> = GetSizeListUseCase(repository)

    @Provides
    fun provideDeleteBottomSizeUseCase(
        repository: SizeRepository<BottomSize>
    ): DeleteSizeUseCase<BottomSize> = DeleteSizeUseCase(repository)

    // ───── 아우터 사이즈 ─────
    @Provides
    fun provideInsertOuterSizeUseCase(
        repository: SizeRepository<OuterSize>
    ): InsertSizeUseCase<OuterSize> = InsertSizeUseCase(repository)

    @Provides
    fun provideGetOuterSizeListUseCase(
        repository: SizeRepository<OuterSize>
    ): GetSizeListUseCase<OuterSize> = GetSizeListUseCase(repository)

    @Provides
    fun provideDeleteOuterSizeUseCase(
        repository: SizeRepository<OuterSize>
    ): DeleteSizeUseCase<OuterSize> = DeleteSizeUseCase(repository)

    // ───── 일체형 사이즈 ─────
    @Provides
    fun provideInsertOnePieceSizeUseCase(
        repository: SizeRepository<OnePieceSize>
    ): InsertSizeUseCase<OnePieceSize> = InsertSizeUseCase(repository)

    @Provides
    fun provideGetOnePieceSizeListUseCase(
        repository: SizeRepository<OnePieceSize>
    ): GetSizeListUseCase<OnePieceSize> = GetSizeListUseCase(repository)

    @Provides
    fun provideDeleteOnePieceSizeUseCase(
        repository: SizeRepository<OnePieceSize>
    ): DeleteSizeUseCase<OnePieceSize> = DeleteSizeUseCase(repository)

    // ───── 신발 사이즈 ─────
    @Provides
    fun provideInsertShoeSizeUseCase(
        repository: SizeRepository<ShoeSize>
    ): InsertSizeUseCase<ShoeSize> = InsertSizeUseCase(repository)

    @Provides
    fun provideGetShoeSizeListUseCase(
        repository: SizeRepository<ShoeSize>
    ): GetSizeListUseCase<ShoeSize> = GetSizeListUseCase(repository)

    @Provides
    fun provideDeleteShoeSizeUseCase(
        repository: SizeRepository<ShoeSize>
    ): DeleteSizeUseCase<ShoeSize> = DeleteSizeUseCase(repository)

    // ───── 액세서리 사이즈 ─────
    @Provides
    fun provideInsertAccessorySizeUseCase(
        repository: SizeRepository<AccessorySize>
    ): InsertSizeUseCase<AccessorySize> = InsertSizeUseCase(repository)

    @Provides
    fun provideGetAccessorySizeListUseCase(
        repository: SizeRepository<AccessorySize>
    ): GetSizeListUseCase<AccessorySize> = GetSizeListUseCase(repository)

    @Provides
    fun provideDeleteAccessorySizeUseCase(
        repository: SizeRepository<AccessorySize>
    ): DeleteSizeUseCase<AccessorySize> = DeleteSizeUseCase(repository)

    // ───── 브랜드 ─────
    @Provides
    fun provideInsertBrandUseCase(
        repository: BrandRepository
    ): InsertBrandUseCase = InsertBrandUseCase(repository)
    @Provides
    fun provideGetBrandListUseCase(
        repository: BrandRepository
    ): GetBrandListUseCase = GetBrandListUseCase(repository)
    @Provides
    fun provideGetBrandByCategoryUseCase(
        repository: BrandRepository
    ): GetBrandListByCategoryUseCase = GetBrandListByCategoryUseCase(repository)
    @Provides
    fun provideDeleteBrandUseCase(
        repository: BrandRepository
    ): DeleteBrandUseCase = DeleteBrandUseCase(repository)
}
