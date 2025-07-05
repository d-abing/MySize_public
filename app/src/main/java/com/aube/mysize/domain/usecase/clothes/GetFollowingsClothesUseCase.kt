package com.aube.mysize.domain.usecase.clothes

import com.aube.mysize.domain.model.clothes.Clothes
import com.aube.mysize.domain.repository.ClothesRepository
import com.google.firebase.firestore.DocumentSnapshot
import javax.inject.Inject

class GetFollowingsClothesUseCase @Inject constructor(
    private val repo: ClothesRepository
) {
    suspend operator fun invoke(
        followings: List<String>,
        pageSize: Int,
        snapshotMap: Map<String, DocumentSnapshot?>
    ): Pair<List<Clothes>, Map<String, DocumentSnapshot?>> {
        val result = mutableListOf<Clothes>()
        val updatedMap = snapshotMap.toMutableMap()

        for (uid in followings) {
            val startAfter = snapshotMap[uid]
            val (items, last) = repo.getUserPublicClothesPaged(uid, pageSize, startAfter)
            result += items
            updatedMap[uid] = last
        }

        return result to updatedMap
    }
}
