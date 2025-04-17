package com.aube.mysize.data.di

import com.aube.mysize.data.database.dao.AccessorySizeDao
import com.aube.mysize.data.database.dao.BodySizeDao
import com.aube.mysize.data.database.dao.BottomSizeDao
import com.aube.mysize.data.database.dao.BrandDao
import com.aube.mysize.data.database.dao.ClothDao
import com.aube.mysize.data.database.dao.OnePieceSizeDao
import com.aube.mysize.data.database.dao.OuterSizeDao
import com.aube.mysize.data.database.dao.ShoeSizeDao
import com.aube.mysize.data.database.dao.TopSizeDao
import com.aube.mysize.data.model.size.AccessorySizeEntity
import com.aube.mysize.data.model.size.BodySizeEntity
import com.aube.mysize.data.model.size.BottomSizeEntity
import com.aube.mysize.data.model.size.OnePieceSizeEntity
import com.aube.mysize.data.model.size.OuterSizeEntity
import com.aube.mysize.data.model.size.ShoeSizeEntity
import com.aube.mysize.data.model.size.TopSizeEntity
import com.aube.mysize.data.model.size.toDomain
import com.aube.mysize.data.repository.BrandRepositoryImpl
import com.aube.mysize.data.repository.ClothRepositoryImpl
import com.aube.mysize.data.repository.SizeRepositoryImpl
import com.aube.mysize.domain.model.AccessorySize
import com.aube.mysize.domain.model.BodySize
import com.aube.mysize.domain.model.BottomSize
import com.aube.mysize.domain.model.OnePieceSize
import com.aube.mysize.domain.model.OuterSize
import com.aube.mysize.domain.model.ShoeSize
import com.aube.mysize.domain.model.TopSize
import com.aube.mysize.domain.model.toEntity
import com.aube.mysize.domain.repository.BrandRepository
import com.aube.mysize.domain.repository.ClothRepository
import com.aube.mysize.domain.repository.SizeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideBodySizeRepository(
        dao: BodySizeDao
    ): SizeRepository<BodySize> = object : SizeRepositoryImpl<BodySize, BodySizeEntity>(
        dao = dao,
        toEntity = { it.toEntity() },
        toDomain = { it.toDomain() }
    ) {}

    @Provides
    fun provideTopSizeRepository(
        dao: TopSizeDao
    ): SizeRepository<TopSize> = object : SizeRepositoryImpl<TopSize, TopSizeEntity>(
        dao = dao,
        toEntity = { it.toEntity() },
        toDomain = { it.toDomain() }
    ) {}

    @Provides
    fun provideBottomSizeRepository(
        dao: BottomSizeDao
    ): SizeRepository<BottomSize> = object : SizeRepositoryImpl<BottomSize, BottomSizeEntity>(
        dao = dao,
        toEntity = { it.toEntity() },
        toDomain = { it.toDomain() }
    ) {}

    @Provides
    fun provideOuterSizeRepository(
        dao: OuterSizeDao
    ): SizeRepository<OuterSize> = object : SizeRepositoryImpl<OuterSize, OuterSizeEntity>(
        dao = dao,
        toEntity = { it.toEntity() },
        toDomain = { it.toDomain() }
    ) {}

    @Provides
    fun provideOnePieceSizeRepository(
        dao: OnePieceSizeDao
    ): SizeRepository<OnePieceSize> = object : SizeRepositoryImpl<OnePieceSize, OnePieceSizeEntity>(
        dao = dao,
        toEntity = { it.toEntity() },
        toDomain = { it.toDomain() }
    ) {}

    @Provides
    fun provideShoeSizeRepository(
        dao: ShoeSizeDao
    ): SizeRepository<ShoeSize> = object : SizeRepositoryImpl<ShoeSize, ShoeSizeEntity>(
        dao = dao,
        toEntity = { it.toEntity() },
        toDomain = { it.toDomain() }
    ) {}

    @Provides
    fun provideAccessorySizeRepository(
        dao: AccessorySizeDao
    ): SizeRepository<AccessorySize> = object : SizeRepositoryImpl<AccessorySize, AccessorySizeEntity>(
        dao = dao,
        toEntity = { it.toEntity() },
        toDomain = { it.toDomain() }
    ) {}

    @Provides
    fun provideBrandRepository(
        dao: BrandDao
    ): BrandRepository = BrandRepositoryImpl(dao)

    @Provides
    fun provideClothRepository(
        dao: ClothDao
    ): ClothRepository = ClothRepositoryImpl(dao)
}
