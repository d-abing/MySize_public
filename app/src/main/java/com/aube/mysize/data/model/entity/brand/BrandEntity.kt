package com.aube.mysize.data.model.entity.brand

import androidx.room.Entity
import com.aube.mysize.domain.model.brand.Brand

@Entity(
    tableName = "brand",
    primaryKeys = ["name", "category"]
)
data class BrandEntity(
    val name: String,
    val category: String
)

fun BrandEntity.toDomain(): Brand = Brand(name, category)

fun Brand.toEntity(): BrandEntity = BrandEntity(name = name, category = category)