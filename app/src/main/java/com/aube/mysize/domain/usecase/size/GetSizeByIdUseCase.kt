package com.aube.mysize.domain.usecase.size

import com.aube.mysize.domain.model.size.Size
import com.aube.mysize.domain.model.size.SizeCategory
import com.aube.mysize.domain.repository.SizeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSizeByIdUseCase @Inject constructor(
    private val repo: SizeRepository
) {
    operator fun invoke(category: SizeCategory, sizeId: String, ownerUid: String): Flow<Size?> {
        return repo.getSizeById(category, sizeId, ownerUid)
    }
}