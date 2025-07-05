package com.aube.mysize.domain.usecase.report

import com.aube.mysize.domain.repository.ReportRepository
import javax.inject.Inject

class SubmitReportUseCase @Inject constructor(
    private val repo: ReportRepository
) {
    suspend operator fun invoke(
        reporterId: String,
        targetType: String,
        targetId: String,
        reason: String
    ) {
        repo.submitReport(reporterId, targetType, targetId, reason)
    }
}
