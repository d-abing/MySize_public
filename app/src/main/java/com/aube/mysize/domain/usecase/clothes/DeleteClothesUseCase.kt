package com.aube.mysize.domain.usecase.clothes

import com.aube.mysize.domain.model.clothes.Clothes
import com.aube.mysize.domain.repository.ClothesRepository

class DeleteClothesUseCase(private val repo: ClothesRepository) {
    suspend operator fun invoke(clothes: Clothes) = repo.delete(clothes)
}
