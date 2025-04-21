package com.aube.mysize.domain.usecase.clothes

import com.aube.mysize.domain.model.clothes.Clothes
import com.aube.mysize.domain.repository.ClothesRepository
import kotlinx.coroutines.flow.Flow

class GetClothesListUseCase(
    private val repository: ClothesRepository
) {
    operator fun invoke(): Flow<List<Clothes>> {
        return repository.getAll()
    }
}
