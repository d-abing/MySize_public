package com.aube.mysize.domain.usecase.size

import com.aube.mysize.domain.model.size.Size
import com.aube.mysize.domain.model.size.SizeCategory
import com.aube.mysize.domain.repository.SizeRepository
import javax.inject.Inject

class DeleteSizeUseCase @Inject constructor(
    private val repo: SizeRepository
) {
    suspend operator fun invoke(category: SizeCategory, size: Size, clothesIds: Set<String>? = null) {
        repo.delete(category, size, clothesIds)
    }
}
