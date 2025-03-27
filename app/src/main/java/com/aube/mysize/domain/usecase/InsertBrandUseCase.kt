package com.aube.mysize.domain.usecase

import com.aube.mysize.domain.repository.BrandRepository

class InsertBrandUseCase(private val repo: BrandRepository) {
    suspend operator fun invoke(name: String, category: String) = repo.insert(name, category)
}