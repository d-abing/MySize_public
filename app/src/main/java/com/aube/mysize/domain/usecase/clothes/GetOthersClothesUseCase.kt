package com.aube.mysize.domain.usecase.clothes

import com.aube.mysize.domain.model.clothes.Clothes
import com.aube.mysize.domain.repository.ClothesRepository
import com.google.firebase.firestore.DocumentSnapshot
import javax.inject.Inject

class GetOthersClothesUseCase @Inject constructor(
    private val repo: ClothesRepository
) {
    suspend operator fun invoke(
        uid: String,
        pageSize: Int,
        startAfter: DocumentSnapshot? = null
    ): Pair<List<Clothes>, DocumentSnapshot?> {
        return repo.getOtherUsersClothesPaged(uid, pageSize, startAfter)
    }
}