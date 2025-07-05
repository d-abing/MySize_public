package com.aube.mysize.domain.usecase.clothes

import com.aube.mysize.domain.model.clothes.Clothes
import com.aube.mysize.domain.repository.ClothesRepository
import com.google.firebase.firestore.DocumentSnapshot

class GetUserPublicClothesUseCase (
    private val repo: ClothesRepository
) {
    suspend operator fun invoke(
        uid: String,
        pageSize: Int,
        startAfter: DocumentSnapshot? = null
    ): Pair<List<Clothes>, DocumentSnapshot?> {
        return repo.getUserPublicClothesPaged(uid, pageSize, startAfter)
    }
}
