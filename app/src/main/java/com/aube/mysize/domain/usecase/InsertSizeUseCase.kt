package com.aube.mysize.domain.usecase

import com.aube.mysize.domain.model.Size
import com.aube.mysize.domain.repository.SizeRepository

class InsertSizeUseCase<T : Size>(
    private val repository: SizeRepository<T>
) {
    suspend operator fun invoke(item: T) {
        repository.insert(item)
    }
}