package com.aube.mysize.domain.usecase.clothes

import com.aube.mysize.domain.model.clothes.Clothes
import com.aube.mysize.domain.repository.ClothesRepository

class InsertClothesUseCase(private val repo: ClothesRepository) {
    suspend operator fun invoke(
        clothes: Clothes,
        imageBytes: ByteArray?,
        isEdit: Boolean
    ): String? = repo.insert(clothes, imageBytes, isEdit)
}