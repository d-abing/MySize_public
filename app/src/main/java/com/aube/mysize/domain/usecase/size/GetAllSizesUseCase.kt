package com.aube.mysize.domain.usecase.size

import com.aube.mysize.domain.model.size.Size
import com.aube.mysize.domain.model.size.SizeCategory
import com.aube.mysize.domain.repository.SizeRepository
import kotlinx.coroutines.flow.Flow

class GetAllSizesUseCase(
    private val repo: SizeRepository
) {
    operator fun invoke(uid: String?): Flow<Map<SizeCategory, List<Size>>> {
        return repo.getAll(uid)
    }
}