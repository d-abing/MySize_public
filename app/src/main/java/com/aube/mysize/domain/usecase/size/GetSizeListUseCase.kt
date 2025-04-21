package com.aube.mysize.domain.usecase.size

import com.aube.mysize.domain.repository.SizeRepository
import kotlinx.coroutines.flow.Flow

class GetSizeListUseCase<T>(
    private val repository: SizeRepository<T>
) {
    operator fun invoke(): Flow<List<T>> {
        return repository.getAll()
    }
}
