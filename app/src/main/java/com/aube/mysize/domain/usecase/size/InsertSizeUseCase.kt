package com.aube.mysize.domain.usecase.size

import com.aube.mysize.domain.model.size.Size
import com.aube.mysize.domain.repository.SizeRepository

class InsertSizeUseCase<T : Size>(
    private val repository: SizeRepository<T>
) {
    suspend operator fun invoke(item: T): Long {
        return repository.insert(item)
    }
}