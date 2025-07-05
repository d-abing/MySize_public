package com.aube.mysize.presentation.viewmodel.clothes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aube.mysize.domain.model.clothes.Clothes
import com.aube.mysize.domain.usecases.ClothesUseCases
import com.aube.mysize.presentation.model.clothes.ClothesSaveModel
import com.aube.mysize.presentation.model.clothes.toDomain
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MyClothesViewModel @Inject constructor(
    private val clothesUseCases: ClothesUseCases,
    private val userInfoCacheManager: UserInfoCacheManager
) : ViewModel() {

    private val _myClothes = MutableStateFlow<List<Clothes>>(emptyList())
    val myClothes: StateFlow<List<Clothes>> = _myClothes.asStateFlow()

    private val _isUploading = MutableStateFlow(false)
    val isUploading: StateFlow<Boolean> = _isUploading.asStateFlow()

    private val _linkedSizeIndex = MutableStateFlow<Map<String, Set<String>>>(emptyMap())
    val linkedSizeIndex: StateFlow<Map<String, Set<String>>> = _linkedSizeIndex.asStateFlow()

    init {
        loadMyClothesList()
    }

    fun loadMyClothesList() {
        viewModelScope.launch {
            val uid = Firebase.auth.currentUser?.uid ?: return@launch
            clothesUseCases.getUserAllClothes(uid).collect {
                _myClothes.value = it
                buildLinkedSizeIndex(it)
                userInfoCacheManager.getNickname(uid)
            }
        }
    }

    suspend fun insert(uiModel: ClothesSaveModel): String? {
        return try {
            _isUploading.value = true
            val id = uiModel.id ?: Firebase.firestore
                .collection("clothes")
                .document(uiModel.createUserId)
                .collection("items")
                .document()
                .id

            val clothes = uiModel.toDomain(id = id)
            clothesUseCases.insertClothes(clothes, uiModel.imageBytes, uiModel.id != null)
        } catch (e: Exception) {
            Timber.tag("MyClothesViewModel").e(e, "❌ insert failed")
            null
        } finally {
            _isUploading.value = false
        }
    }

    fun delete(clothes: Clothes) {
        viewModelScope.launch {
            clothesUseCases.deleteClothes(clothes)
        }
    }

    private fun buildLinkedSizeIndex(clothesList: List<Clothes>) {
        val index = mutableMapOf<String, MutableSet<String>>()
        clothesList.forEach { clothes ->
            clothes.linkedSizes.forEach { group ->
                group.sizeIds.forEach { sizeId ->
                    val key = "${group.category}_$sizeId"
                    index.getOrPut(key) { mutableSetOf() }.add(clothes.id)
                }
            }
        }
        _linkedSizeIndex.value = index
    }
}
