package com.aube.mysize.domain.usecase.report

import com.aube.mysize.domain.repository.ReportRepository
import javax.inject.Inject

class GetReportedClothesUseCase @Inject constructor(
    private val repo: ReportRepository
) {
    suspend operator fun invoke(): Set<String> {
        return repo.getReportedClothes()
    }
}
