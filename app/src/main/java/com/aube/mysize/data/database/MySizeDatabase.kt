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
import com.aube.mysize.data.database.dao.TopSizeDao
import com.aube.mysize.data.model.brand.BrandEntity
import com.aube.mysize.data.model.clothes.ClothesEntity
import com.aube.mysize.data.model.size.AccessorySizeEntity
import com.aube.mysize.data.model.size.BodySizeEntity
import com.aube.mysize.data.model.size.BottomSizeEntity
import com.aube.mysize.data.model.size.OnePieceSizeEntity
import com.aube.mysize.data.model.size.OuterSizeEntity
import com.aube.mysize.data.model.size.ShoeSizeEntity
import com.aube.mysize.data.model.size.TopSizeEntity

const val DATABASE_VERSION = 19

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
        ClothesEntity::class
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
}