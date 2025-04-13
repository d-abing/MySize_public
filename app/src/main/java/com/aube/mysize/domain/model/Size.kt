package com.aube.mysize.domain.model

import java.time.LocalDate

interface Size {
    val id: Int
    val date: LocalDate
}

interface ClothSize : Size {
    val type: String
    val brand: String
    val sizeLabel: String
}
