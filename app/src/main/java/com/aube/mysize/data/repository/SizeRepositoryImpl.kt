package com.aube.mysize.data.repository

import com.aube.mysize.app.NetworkMonitor
import com.aube.mysize.data.database.dao.SizeDao
import com.aube.mysize.data.model.dto.clothes.ClothesDTO
import com.aube.mysize.data.model.dto.size.AccessorySizeDTO
import com.aube.mysize.data.model.dto.size.BodySizeDTO
import com.aube.mysize.data.model.dto.size.BottomSizeDTO
import com.aube.mysize.data.model.dto.size.OnePieceSizeDTO
import com.aube.mysize.data.model.dto.size.OuterSizeDTO
import com.aube.mysize.data.model.dto.size.ShoeSizeDTO
import com.aube.mysize.data.model.dto.size.SizeDTO
import com.aube.mysize.data.model.dto.size.TopSizeDTO
import com.aube.mysize.data.model.dto.size.clazz
import com.aube.mysize.data.model.dto.size.collectionName
import com.aube.mysize.data.model.dto.size.toDTO
import com.aube.mysize.data.model.entity.size.SizeEntity
import com.aube.mysize.data.model.entity.size.toDomain
import com.aube.mysize.data.model.entity.size.toEntity
import com.aube.mysize.domain.model.clothes.LinkedSizeGroup
import com.aube.mysize.domain.model.size.Size
import com.aube.mysize.domain.model.size.SizeCategory
import com.aube.mysize.domain.model.size.withId
import com.aube.mysize.domain.repository.SizeRepository
import com.aube.mysize.utils.toTimestamp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SizeRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val daoMap: Map<SizeCategory, @JvmSuppressWildcards SizeDao<out SizeEntity>>,
) : SizeRepository {

    override suspend fun insert(category: SizeCategory, size: Size): String {
        val uid = size.uid
        val isBody = category == SizeCategory.BODY

        val id = if (isBody) {
            "current"
        } else {
            size.id.takeIf { it.isNotBlank() }
                ?: firestore.collection("sizes")
                    .document(uid)
                    .collection(category.collectionName())
                    .document().id
        }

        val newSize = size.withId(id)
        val entity = newSize.toEntity()
        val dto = newSize.toDTO()

        firestore.collection("sizes")
            .document(uid)
            .collection(category.collectionName())
            .document(id)
            .set(dto)

        (daoMap[category] as? SizeDao<SizeEntity>)?.insert(entity)

        return id
    }

    override suspend fun delete(category: SizeCategory, size: Size, clothesIds: Set<String>?) {
        val uid = size.uid
        val id = size.id
        val entity = size.toEntity()

        if (NetworkMonitor.networkAvailable.value) {
            firestore.collection("sizes")
                .document(uid)
                .collection(category.collectionName())
                .document(id)
                .delete()
                .await()

            clothesIds?.forEach { clothesId ->
                val docRef = firestore.collection("clothes")
                    .document(uid)
                    .collection("items")
                    .document(clothesId)

                val snapshot = docRef.get().await()
                val dto = snapshot.toObject(ClothesDTO::class.java) ?: return@forEach

                val updatedLinkedSizes = dto.linkedSizes.mapNotNull { group ->
                    if (group.category != category.name) return@mapNotNull group
                    val filtered = group.sizeIds.filter { it != id }
                    if (filtered.isEmpty()) null else LinkedSizeGroup(group.category, filtered)
                }

                if (updatedLinkedSizes != dto.linkedSizes) {
                    val updated = dto.copy(
                        linkedSizes = updatedLinkedSizes,
                        updatedAt = LocalDateTime.now().toTimestamp()
                    )
                    docRef.set(updated).await()
                }
            }
        } else {
            (daoMap[category] as? SizeDao<SizeEntity>)?.delete(entity)
        }
    }

    override fun getAll(uid: String?): Flow<Map<SizeCategory, List<Size>>> = channelFlow {
        val flows = daoMap.map { (category, dao) ->
            (dao as SizeDao<SizeEntity>).getAll().map { list ->
                category to list.map { it.toDomain() }
            }
        }

        val combined = combine(flows) { it.toMap() }
        val job = launch {
            combined.collect { send(it) }
        }

        if (uid != null && NetworkMonitor.networkAvailable.value) {
            try {
                SizeCategory.entries.forEach { category ->
                    val sizes = fetchSizesFromFirestore(uid, category)
                    val dao = daoMap[category] as? SizeDao<SizeEntity>
                    dao?.replaceAll(sizes.map { it.toEntity() })
                }
            } catch (e: Exception) {
                Timber.tag("SizeRepositoryImpl").e(e, "Firestore fetch failed")
            }
        }

        awaitClose { job.cancel() }
    }

    private suspend fun fetchSizesFromFirestore(uid: String, category: SizeCategory): List<Size> = when (category) {
        SizeCategory.BODY -> {
            val doc = firestore.collection("sizes").document(uid)
                .collection(category.collectionName())
                .document("current")
                .get().await()
            val body = doc.toObject(BodySizeDTO::class.java)?.toDomain()
            body?.let { listOf(it) } ?: emptyList()
        }

        else -> {
            val snapshot = firestore.collection("sizes")
                .document(uid)
                .collection(category.collectionName())
                .get().await()
            when (category) {
                SizeCategory.TOP -> snapshot.toObjects(TopSizeDTO::class.java).map { it.toDomain() }
                SizeCategory.BOTTOM -> snapshot.toObjects(BottomSizeDTO::class.java).map { it.toDomain() }
                SizeCategory.SHOE -> snapshot.toObjects(ShoeSizeDTO::class.java).map { it.toDomain() }
                SizeCategory.OUTER -> snapshot.toObjects(OuterSizeDTO::class.java).map { it.toDomain() }
                SizeCategory.ONE_PIECE -> snapshot.toObjects(OnePieceSizeDTO::class.java).map { it.toDomain() }
                SizeCategory.ACCESSORY -> snapshot.toObjects(AccessorySizeDTO::class.java).map { it.toDomain() }
                else -> emptyList()
            }
        }
    }

    override fun getSizeById(
        category: SizeCategory,
        sizeId: String,
        ownerUid: String,
    ): Flow<Size?> = flow {
        val snapshot = firestore
            .collection("sizes")
            .document(ownerUid)
            .collection(category.collectionName())
            .document(sizeId)
            .get()
            .await()

        val dto: SizeDTO? = snapshot.toObject(category.clazz)
        emit(dto?.toDomain())
    }
}
