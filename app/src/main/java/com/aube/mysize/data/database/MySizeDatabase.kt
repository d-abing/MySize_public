package com.aube.mysize.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.aube.mysize.data.database.dao.AccessorySizeDao
import com.aube.mysize.data.database.dao.BodySizeDao
import com.aube.mysize.data.database.dao.BottomSizeDao
import com.aube.mysize.data.database.dao.BrandDao
import com.aube.mysize.data.database.dao.ClothesDao
import com.aube.mysize.data.database.dao.OnePieceSizeDao
import com.aube.mysize.data.database.dao.OuterSizeDao
import com.aube.mysize.data.database.dao.ShoeSizeDao
import com.aube.mysize.data.database.dao.ShopDao
import com.aube.mysize.data.database.dao.TopSizeDao
import com.aube.mysize.data.database.dao.UserDao
import com.aube.mysize.data.model.entity.brand.BrandEntity
import com.aube.mysize.data.model.entity.clothes.ClothesEntity
import com.aube.mysize.data.model.entity.recommend.RecommendedShopEntity
import com.aube.mysize.data.model.entity.size.AccessorySizeEntity
import com.aube.mysize.data.model.entity.size.BodySizeEntity
import com.aube.mysize.data.model.entity.size.BottomSizeEntity
import com.aube.mysize.data.model.entity.size.OnePieceSizeEntity
import com.aube.mysize.data.model.entity.size.OuterSizeEntity
import com.aube.mysize.data.model.entity.size.ShoeSizeEntity
import com.aube.mysize.data.model.entity.size.TopSizeEntity
import com.aube.mysize.data.model.entity.user.UserEntity

const val DATABASE_VERSION = 1

@Database(
    entities = [
        BodySizeEntity::class,
        TopSizeEntity::class,
        BottomSizeEntity::class,
        OuterSizeEntity::class,
        OnePieceSizeEntity::class,
        ShoeSizeEntity::class,
        AccessorySizeEntity::class,
        BrandEntity::class,
        ClothesEntity::class,
        UserEntity::class,
        RecommendedShopEntity::class
    ],
    version = DATABASE_VERSION
)

@TypeConverters(TypeConverter::class)
abstract class MySizeDatabase : RoomDatabase() {
    abstract fun bodySizeDao(): BodySizeDao
    abstract fun topSizeDao(): TopSizeDao
    abstract fun bottomSizeDao(): BottomSizeDao
    abstract fun outerSizeDao(): OuterSizeDao
    abstract fun onePieceSizeDao(): OnePieceSizeDao
    abstract fun shoeSizeDao(): ShoeSizeDao
    abstract fun accessorySizeDao(): AccessorySizeDao
    abstract fun brandDao(): BrandDao
    abstract fun clothesDao(): ClothesDao
    abstract fun userDao(): UserDao
    abstract fun shopDao(): ShopDao
}