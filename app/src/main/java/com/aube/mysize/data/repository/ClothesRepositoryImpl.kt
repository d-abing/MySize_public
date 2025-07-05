package com.aube.mysize.data.repository

import com.aube.mysize.data.database.dao.ClothesDao
import com.aube.mysize.data.model.dto.clothes.ClothesDTO
import com.aube.mysize.data.model.dto.clothes.toDTO
import com.aube.mysize.data.model.dto.clothes.toDomain
import com.aube.mysize.data.model.entity.clothes.toDomain
import com.aube.mysize.data.model.entity.clothes.toEntity
import com.aube.mysize.domain.model.clothes.Clothes
import com.aube.mysize.domain.repository.ClothesRepository
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageMetadata
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import java.time.LocalDateTime
import javax.inject.Inject

class ClothesRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage,
    private val dao: ClothesDao,
) : ClothesRepository {

    override suspend fun insert(clothes: Clothes, imageBytes: ByteArray?, isEdit: Boolean): String? {
        val id = clothes.id
        val uid = clothes.createUserId
        val docRef = firestore.collection("clothes").document(uid).collection("items").document(id)
        val imageRef = storage.reference.child("clothes/$id.jpg")

        try {
            val metadata = StorageMetadata.Builder()
                .setCustomMetadata("owner", uid)
                .build()

            val updatedClothes = if (imageBytes != null) {
                imageRef.putBytes(imageBytes, metadata).await()
                val url = imageRef.downloadUrl.await().toString()
                clothes.copy(
                    imageUrl = url,
                    updatedAt = if (isEdit) LocalDateTime.now() else clothes.updatedAt
                )
            } else {
                clothes.copy(updatedAt = LocalDateTime.now())
            }

            dao.insert(updatedClothes.toEntity())
            docRef.set(updatedClothes.toDTO()).await()

            return updatedClothes.imageUrl
        } catch (e: Exception) {
            Timber.tag("ClothesRepo").e(e, "Error during insert")
            return null
        }
    }

    override fun getUserAllClothes(uid: String?): Flow<List<Clothes>> = channelFlow {
        launch {
            if (uid == null) return@launch
            try {
                val snapshot = firestore.collection("clothes")
                    .document(uid).collection("items")
                    .orderBy("createdAt", Query.Direction.DESCENDING)
                    .get().await()

                val clothesList = snapshot.documents.mapNotNull { it.toObject(ClothesDTO::class.java)?.toDomain() }
                send(clothesList)
                dao.replaceAll(clothesList.map { it.toEntity() })
            } catch (e: Exception) {
                Timber.tag("ClothesRepo").e(e, "Firestore fetch failed")
            }
        }

        dao.getAll().map { list -> list.map { it.toDomain() } }.distinctUntilChanged().collect { send(it) }
    }

    override suspend fun getUserPublicClothesPaged(uid: String, pageSize: Int, startAfter: DocumentSnapshot?): Pair<List<Clothes>, DocumentSnapshot?> {
        var query = firestore.collection("clothes").document(uid).collection("items")
            .whereEqualTo("visibility", "PUBLIC")
            .orderBy("createdAt", Query.Direction.DESCENDING)
            .limit(pageSize.toLong())

        if (startAfter != null) query = query.startAfter(startAfter)

        val snapshot = query.get().await()
        val clothesList = snapshot.documents.mapNotNull { it.toObject(ClothesDTO::class.java)?.toDomain() }
        return clothesList to snapshot.documents.lastOrNull()
    }

    override suspend fun getOtherUsersClothesPaged(uid: String, pageSize: Int, startAfter: DocumentSnapshot?): Pair<List<Clothes>, DocumentSnapshot?> {
        var query = firestore.collectionGroup("items")
            .whereEqualTo("visibility", "PUBLIC")
            .orderBy("createdAt", Query.Direction.DESCENDING)
            .limit(pageSize.toLong())

        if (startAfter != null) query = query.startAfter(startAfter)

        val snapshot = query.get().await()
        val clothesList = snapshot.documents
            .mapNotNull { it.toObject(ClothesDTO::class.java)?.toDomain() }
            .filter { it.createUserId != uid }

        return clothesList to snapshot.documents.lastOrNull()
    }

    override suspend fun getById(id: String): Clothes? {
        val local = dao.getById(id)?.toDomain()
        if (local != null) return local

        val snapshot = firestore.collectionGroup("items")
            .whereEqualTo("id", id).limit(1).get().await()

        return snapshot.documents.firstOrNull()?.toObject(ClothesDTO::class.java)?.toDomain()
    }

    override suspend fun getClothesByTag(tag: String, pageSize: Int, lastSnapshot: DocumentSnapshot?): Pair<List<Clothes>, DocumentSnapshot?> {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return emptyList<Clothes>() to null

        val query = firestore.collectionGroup("items")
            .whereArrayContains("tags", tag)
            .orderBy("createdAt", Query.Direction.DESCENDING)
            .let { if (lastSnapshot != null) it.startAfter(lastSnapshot) else it }
            .limit(pageSize.toLong())

        val snapshot = query.get().await()

        val clothesList = snapshot.documents
            .mapNotNull { it.toObject(ClothesDTO::class.java)?.toDomain() }
            .filter { it.createUserId != uid }
        return clothesList to snapshot.documents.lastOrNull()
    }

    override suspend fun delete(clothes: Clothes) {
        firestore.collection("clothes")
            .document(clothes.createUserId)
            .collection("items")
            .document(clothes.id).delete().await()

        storage.reference.child("clothes/${clothes.id}.jpg").delete().await()
        dao.delete(clothes.toEntity())
    }

    override suspend fun saveRecentView(clothesId: String) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val recentViewsRef = firestore.collection("users").document(uid).collection("recentViews")

        val snapshot = recentViewsRef.orderBy("viewedAt", Query.Direction.ASCENDING).get().await()
        if (snapshot.size() >= 20) snapshot.documents.firstOrNull()?.reference?.delete()?.await()

        recentViewsRef.document(clothesId).set(mapOf("viewedAt" to Timestamp.now())).await()
    }

    override suspend fun getRecentViews(): List<Clothes> {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return emptyList()

        val recentSnapshot = firestore.collection("users").document(uid).collection("recentViews")
            .orderBy("viewedAt", Query.Direction.DESCENDING).limit(20).get().await()

        val clothesIds = recentSnapshot.documents.map { it.id }

        return clothesIds.mapNotNull { id ->
            firestore.collectionGroup("items")
                .whereEqualTo("id", id).limit(1).get().await()
                .documents.firstOrNull()?.toObject(ClothesDTO::class.java)?.toDomain()
        }
    }

    override suspend fun deleteRecentViews() {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return

        val snapshot = firestore.collection("users").document(uid).collection("recentViews")
            .orderBy("viewedAt", Query.Direction.DESCENDING).limit(20).get().await()

        if (snapshot.isEmpty) return

        val batch = firestore.batch()
        snapshot.documents.forEach { batch.delete(it.reference) }
        batch.commit().await()
    }
}