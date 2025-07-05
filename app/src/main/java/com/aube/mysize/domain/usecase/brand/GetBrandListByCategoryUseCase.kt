package com.aube.mysize.domain.usecase.brand

import com.aube.mysize.domain.model.brand.Brand
import com.aube.mysize.domain.repository.BrandRepository
import kotlinx.coroutines.flow.Flow

class GetBrandListByCategoryUseCase(private val repo: BrandRepository) {
    operator fun invoke(category: String): Flow<List<Brand>> = repo.getByCategory(category)
}