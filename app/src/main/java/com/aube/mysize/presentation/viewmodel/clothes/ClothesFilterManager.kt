package com.aube.mysize.presentation.viewmodel.clothes

import com.aube.mysize.domain.model.clothes.Clothes
import com.aube.mysize.domain.usecases.BlockingUseCases
import com.aube.mysize.domain.usecases.ReportUseCases
import javax.inject.Inject

class ClothesFilterManager @Inject constructor(
    private val reportUseCases: ReportUseCases,
    private val blockingUseCases: BlockingUseCases,
) {
    private var reportedClothesIds: Set<String>? = null
    private var blockedUserIds: Set<String>? = null
    private var blockedMeUserIds: Set<String>? = null

    suspend fun filterClothes(input: List<Clothes>): List<Clothes> {
        val reportedIds = getReportedClothes()
        val blockedIds = getBlockedUserIds()
        val blockedMeIds = getBlockedMeUserIds()

        return input.filterNot { clothes ->
            clothes.id in reportedIds ||
            clothes.createUserId in blockedIds ||
            clothes.createUserId in blockedMeIds
        }
    }

    private suspend fun getReportedClothes(): Set<String> {
        if (reportedClothesIds == null) {
            refreshReportedClothes()
        }
        return reportedClothesIds!!
    }

    private suspend fun getBlockedUserIds(): Set<String> {
        if (blockedUserIds == null) {
            refreshBlockedUsers()
        }
        return blockedUserIds!!
    }

    private suspend fun getBlockedMeUserIds(): Set<String> {
        if (blockedMeUserIds == null) {
            refreshBlockedMeUsers()
        }
        return blockedMeUserIds!!
    }

    suspend fun refreshReportedClothes() {
        reportedClothesIds = reportUseCases.getReportedClothes()
    }

    suspend fun refreshBlockedUsers() {
        blockedUserIds = blockingUseCases.getBlockedUsers().map { it.uid }.toSet()
    }

    suspend fun refreshBlockedMeUsers() {
        blockedMeUserIds = blockingUseCases.getBlockedMeUsers()
    }

    fun deleteBlockedUser(userId: String) {
        blockedUserIds = blockedUserIds?.minus(userId)
    }

    fun clearCache() {
        reportedClothesIds = null
        blockedUserIds = null
        blockedMeUserIds = null
    }
}
