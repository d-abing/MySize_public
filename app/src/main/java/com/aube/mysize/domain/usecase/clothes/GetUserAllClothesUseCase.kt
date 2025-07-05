package com.aube.mysize.domain.usecase.clothes

import com.aube.mysize.domain.model.clothes.Clothes
import com.aube.mysize.domain.repository.ClothesRepository
import kotlinx.coroutines.flow.Flow

class GetUserAllClothesUseCase (
    private val repo: ClothesRepository
) {
    operator fun invoke(uid: String?): Flow<List<Clothes>> {
        return repo.getUserAllClothes(uid)
    }
}
