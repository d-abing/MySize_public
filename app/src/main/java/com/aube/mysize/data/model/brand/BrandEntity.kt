package com.aube.mysize.data.model.brand

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "brand"
)
data class BrandEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val category: String
)
