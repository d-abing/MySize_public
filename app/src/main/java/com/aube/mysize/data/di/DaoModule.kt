package com.aube.mysize.data.di

import com.aube.mysize.data.database.MySizeDatabase
import com.aube.mysize.data.database.dao.AccessorySizeDao
import com.aube.mysize.data.database.dao.BodySizeDao
import com.aube.mysize.data.database.dao.BottomSizeDao
import com.aube.mysize.data.database.dao.BrandDao
import com.aube.mysize.data.database.dao.ClothesDao
import com.aube.mysize.data.database.dao.OnePieceSizeDao
import com.aube.mysize.data.database.dao.OuterSizeDao
import com.aube.mysize.data.database.dao.ShoeSizeDao
import com.aube.mysize.data.database.dao.ShopDao
import com.aube.mysize.data.database.dao.SizeDao
import com.aube.mysize.data.database.dao.TopSizeDao
import com.aube.mysize.data.database.dao.UserDao
import com.aube.mysize.data.model.entity.size.SizeEntity
import com.aube.mysize.domain.model.size.SizeCategory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {
    @Provides fun provideBodySizeDao(db: MySizeDatabase): BodySizeDao = db.bodySizeDao()
    @Provides fun provideTopSizeDao(db: MySizeDatabase): TopSizeDao = db.topSizeDao()
    @Provides fun provideBottomSizeDao(db: MySizeDatabase): BottomSizeDao = db.bottomSizeDao()
    @Provides fun provideOuterSizeDao(db: MySizeDatabase): OuterSizeDao = db.outerSizeDao()
    @Provides fun provideOnePieceSizeDao(db: MySizeDatabase): OnePieceSizeDao = db.onePieceSizeDao()
    @Provides fun provideShoeSizeDao(db: MySizeDatabase): ShoeSizeDao = db.shoeSizeDao()
    @Provides fun provideAccessorySizeDao(db: MySizeDatabase): AccessorySizeDao = db.accessorySizeDao()

    @Provides
    @Singleton
    fun provideSizeDaoMap(
        topSizeDao: TopSizeDao,
        bottomSizeDao: BottomSizeDao,
        outerSizeDao: OuterSizeDao,
        onePieceSizeDao: OnePieceSizeDao,
        shoeSizeDao: ShoeSizeDao,
        accessorySizeDao: AccessorySizeDao,
        bodySizeDao: BodySizeDao
    ): Map<SizeCategory, @JvmSuppressWildcards SizeDao<out SizeEntity>> {
        return mapOf(
            SizeCategory.TOP to topSizeDao,
            SizeCategory.BOTTOM to bottomSizeDao,
            SizeCategory.OUTER to outerSizeDao,
            SizeCategory.ONE_PIECE to onePieceSizeDao,
            SizeCategory.SHOE to shoeSizeDao,
            SizeCategory.ACCESSORY to accessorySizeDao,
            SizeCategory.BODY to bodySizeDao
        )
    }

    @Provides fun provideBrandDao(db: MySizeDatabase): BrandDao = db.brandDao()

    @Provides fun provideClothesDao(db: MySizeDatabase): ClothesDao = db.clothesDao()

    @Provides fun provideUserDao(db: MySizeDatabase): UserDao = db.userDao()

    @Provides fun provideShopDao(db: MySizeDatabase): ShopDao = db.shopDao()
}