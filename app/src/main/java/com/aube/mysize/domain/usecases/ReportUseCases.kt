package com.aube.mysize.domain.usecases

import com.aube.mysize.domain.usecase.report.GetReportedClothesUseCase
import com.aube.mysize.domain.usecase.report.SubmitReportUseCase

data class ReportUseCases(
    val submitReport: SubmitReportUseCase,
    val getReportedClothes: GetReportedClothesUseCase
)
