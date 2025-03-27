package com.aube.mysize.domain.usecase

import com.aube.mysize.domain.repository.BrandRepository
import kotlinx.coroutines.flow.Flow

class GetBrandListByCategoryUseCase(private val repo: BrandRepository) {
    operator fun invoke(category: String): Flow<List<String>> = repo.getByCategory(category)
}