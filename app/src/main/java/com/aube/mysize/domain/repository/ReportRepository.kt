package com.aube.mysize.domain.repository

interface ReportRepository {
    suspend fun submitReport(reporterId: String, targetType: String, targetId: String, reason: String)
    suspend fun getReportedClothes(): Set<String>
}
