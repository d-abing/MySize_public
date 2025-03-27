package com.aube.mysize.domain.usecase

import com.aube.mysize.domain.repository.BrandRepository
import kotlinx.coroutines.flow.Flow

class GetBrandListUseCase(private val repo: BrandRepository) {
    operator fun invoke(): Flow<List<String>> = repo.getAll()
}
