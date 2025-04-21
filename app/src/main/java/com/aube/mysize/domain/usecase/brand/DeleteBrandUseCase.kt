package com.aube.mysize.domain.usecase.brand

import com.aube.mysize.domain.repository.BrandRepository

class DeleteBrandUseCase(private val repo: BrandRepository) {
    suspend operator fun invoke(name: String) = repo.delete(name)
}
