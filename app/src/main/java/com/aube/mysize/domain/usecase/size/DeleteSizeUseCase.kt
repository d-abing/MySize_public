package com.aube.mysize.domain.usecase.size

import com.aube.mysize.domain.repository.SizeRepository

class DeleteSizeUseCase<T>(
    private val repository: SizeRepository<T>
) {
    suspend operator fun invoke(item: T) {
        repository.delete(item)
    }
}
