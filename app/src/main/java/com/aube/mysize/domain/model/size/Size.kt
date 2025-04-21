package com.aube.mysize.domain.model.size

import java.time.LocalDate

interface Size {
    val id: Int
    val date: LocalDate
}

interface ClothesSize : Size {
    val type: String
    val brand: String
    val sizeLabel: String
}
