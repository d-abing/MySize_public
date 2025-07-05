package com.aube.mysize.data.repository

import com.aube.mysize.domain.repository.ReportRepository
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ReportRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : ReportRepository {

    companion object {
        private const val COLLECTION_REPORTS = "reports"
        private const val SUBCOLLECTION_ITEMS = "reportItems"
        private const val TARGET_TYPE_CLOTHES = "clothes"
    }

    override suspend fun submitReport(
        reporterId: String,
        targetType: String,
        targetId: String,
        reason: String
    ) {
        val docId = "${targetType}_$targetId"
        val report = mapOf(
            "targetType" to targetType,
            "targetId" to targetId,
            "reason" to reason,
            "timestamp" to FieldValue.serverTimestamp()
        )

        firestore
            .collection(COLLECTION_REPORTS)
            .document(reporterId)
            .collection(SUBCOLLECTION_ITEMS)
            .document(docId)
            .set(report)
            .await()
    }

    override suspend fun getReportedClothes(): Set<String> {
        val userId = Firebase.auth.currentUser?.uid ?: return emptySet()

        return try {
            val snapshot = firestore
                .collection(COLLECTION_REPORTS)
                .document(userId)
                .collection(SUBCOLLECTION_ITEMS)
                .get().await()

            snapshot.documents.mapNotNull { doc ->
                val type = doc.getString("targetType")
                val id = doc.getString("targetId")
                if (type == TARGET_TYPE_CLOTHES && !id.isNullOrBlank()) id else null
            }.toSet()
        } catch (e: Exception) {
            emptySet()
        }
    }
}