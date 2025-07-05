package com.aube.mysize.domain.usecase.clothes

import com.aube.mysize.domain.repository.ClothesRepository

class ClearRecentViewsUseCase(private val repo: ClothesRepository) {
    suspend operator fun invoke() = repo.deleteRecentViews()
}
