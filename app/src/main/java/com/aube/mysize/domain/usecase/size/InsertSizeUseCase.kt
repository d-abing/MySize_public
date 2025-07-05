package com.aube.mysize.domain.usecase.size

import com.aube.mysize.domain.model.size.Size
import com.aube.mysize.domain.model.size.SizeCategory
import com.aube.mysize.domain.repository.SizeRepository
import javax.inject.Inject

class InsertSizeUseCase @Inject constructor(
    private val repo: SizeRepository
) {
    suspend operator fun invoke(category: SizeCategory, size: Size): String {
        return repo.insert(category, size)
    }
}
