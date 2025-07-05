package com.aube.mysize.domain.usecase.clothes

import com.aube.mysize.domain.model.clothes.Clothes
import com.aube.mysize.domain.repository.ClothesRepository
import javax.inject.Inject

class GetRecentViewsUseCase @Inject constructor(
    private val repo: ClothesRepository
) {
    suspend operator fun invoke(): List<Clothes> {
        return repo.getRecentViews()
    }
}
