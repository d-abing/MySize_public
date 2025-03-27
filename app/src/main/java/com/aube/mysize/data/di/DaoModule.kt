package com.aube.mysize.data.di

import com.aube.mysize.data.database.MySizeDatabase
import com.aube.mysize.data.database.dao.AccessorySizeDao
import com.aube.mysize.data.database.dao.BodySizeDao
import com.aube.mysize.data.database.dao.BottomSizeDao
import com.aube.mysize.data.database.dao.BrandDao
import com.aube.mysize.data.database.dao.OnePieceSizeDao
import com.aube.mysize.data.database.dao.OuterSizeDao
import com.aube.mysize.data.database.dao.ShoeSizeDao
import com.aube.mysize.data.database.dao.TopSizeDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

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
    @Provides fun provideBrandDao(db: MySizeDatabase): BrandDao = db.brandDao()
}