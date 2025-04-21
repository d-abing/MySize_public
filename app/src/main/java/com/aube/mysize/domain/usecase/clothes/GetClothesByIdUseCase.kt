package com.aube.mysize.domain.usecase.clothes

import com.aube.mysize.domain.model.clothes.Clothes
import com.aube.mysize.domain.repository.ClothesRepository

class GetClothesByIdUseCase(
    private val repository: ClothesRepository
) {
    suspend operator fun invoke(id: Int): Clothes? {
        return repository.getById(id)
    }
}
