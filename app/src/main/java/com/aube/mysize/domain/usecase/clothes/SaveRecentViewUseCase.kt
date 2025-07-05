package com.aube.mysize.domain.usecase.clothes

import com.aube.mysize.domain.repository.ClothesRepository
import javax.inject.Inject

class SaveRecentViewUseCase @Inject constructor(
    private val repo: ClothesRepository
) {
    suspend operator fun invoke(clothesId: String) {
        repo.saveRecentView(clothesId)
    }
}
