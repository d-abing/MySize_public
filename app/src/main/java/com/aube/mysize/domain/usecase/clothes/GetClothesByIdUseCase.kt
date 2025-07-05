package com.aube.mysize.domain.usecase.clothes

import com.aube.mysize.domain.model.clothes.Clothes
import com.aube.mysize.domain.repository.ClothesRepository

class GetClothesByIdUseCase(
    private val repo: ClothesRepository
) {
    suspend operator fun invoke(id: String): Clothes? {
        return repo.getById(id)
    }
}
