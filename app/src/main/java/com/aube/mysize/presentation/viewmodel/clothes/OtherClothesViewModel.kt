package com.aube.mysize.presentation.viewmodel.clothes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aube.mysize.domain.model.clothes.Clothes
import com.aube.mysize.domain.usecases.ClothesUseCases
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.DocumentSnapshot
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OtherClothesViewModel @Inject constructor(
    private val clothesUseCases: ClothesUseCases,
    private val clothesFilterManager: ClothesFilterManager
) : ViewModel() {

    private val _othersClothes = MutableStateFlow<List<Clothes>>(emptyList())
    val othersClothes: StateFlow<List<Clothes>> = _othersClothes

    private val _recentClothes = MutableStateFlow<List<Clothes>>(emptyList())
    val recentClothes: StateFlow<List<Clothes>> = _recentClothes.asStateFlow()

    private val _followingClothes = MutableStateFlow<List<Clothes>>(emptyList())
    val followingClothes: StateFlow<List<Clothes>> = _followingClothes

    private val _tagFilteredClothes = MutableStateFlow<List<Clothes>>(emptyList())
    val tagFilteredClothes: StateFlow<List<Clothes>> = _tagFilteredClothes

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing

    private var lastSnapshot: DocumentSnapshot? = null
    private var isLoading = false
    private var hasNext = true
    private val pageSize = 20

    private var followingSnapshots = mutableMapOf<String, DocumentSnapshot?>()
    private var isLoadingFollowing = false
    private var hasNextFollowing = true

    private var tagLastSnapshot: DocumentSnapshot? = null
    private var isLoadingTag = false
    private var hasNextTag = true

    init {
        loadOtherClothesList()
    }

    fun loadOtherClothesList(onComplete: (() -> Unit)? = null) {
        viewModelScope.launch {
            if (isLoading || !hasNext) {
                onComplete?.invoke()
                return@launch
            }

            isLoading = true
            val uid = Firebase.auth.currentUser?.uid ?: return@launch

            val (newItems, last) = clothesUseCases.getOthersClothes(uid, pageSize, lastSnapshot)
            val filtered = clothesFilterManager.filterClothes(newItems)

            _othersClothes.update { current -> current + filtered }

            lastSnapshot = last
            hasNext = last != null && filtered.isNotEmpty()
            isLoading = false

            onComplete?.invoke()
        }
    }

    fun refreshOthersClothesList() {
        _isRefreshing.value = true
        _othersClothes.value = emptyList()
        lastSnapshot = null
        hasNext = true

        loadOtherClothesList {
            _isRefreshing.value = false
        }
    }

    fun refreshReportedClothes() {
        viewModelScope.launch {
            clothesFilterManager.refreshReportedClothes()
        }
    }

    fun refreshBlockedUsers() {
        viewModelScope.launch {
            clothesFilterManager.refreshBlockedUsers()
        }
    }

    fun saveRecentClothesView(clothesId: String) {
        viewModelScope.launch {
            clothesUseCases.saveRecentView(clothesId)
        }
    }

    fun loadRecentClothes() {
        viewModelScope.launch {
            val recentViews = clothesUseCases.getRecentViews()
            val filtered = clothesFilterManager.filterClothes(recentViews)
            _recentClothes.value = filtered
        }
    }

    fun deleteAllHistory() {
        viewModelScope.launch {
            clothesUseCases.clearRecentViews()
            _recentClothes.value = emptyList()
        }
    }

    fun loadFollowingClothesPage(followingUids: List<String>) {
        viewModelScope.launch {
            if (isLoadingFollowing || !hasNextFollowing) return@launch
            isLoadingFollowing = true

            val (newItems, updatedSnapshots) = clothesUseCases.getFollowingsClothes(followingUids, pageSize, followingSnapshots)
            val filtered = clothesFilterManager.filterClothes(newItems)

            _followingClothes.update { it + filtered }
            followingSnapshots.putAll(updatedSnapshots)

            hasNextFollowing = followingUids.any { uid ->
                updatedSnapshots[uid] != null
            }

            isLoadingFollowing = false
        }
    }


    fun clearTagSearchState() {
        _tagFilteredClothes.value = emptyList()
        tagLastSnapshot = null
        hasNextTag = true
    }

    fun loadClothesByTag(tag: String) {
        viewModelScope.launch {
            if (isLoadingTag || !hasNextTag) return@launch
            isLoadingTag = true

            val (newItems, last) = clothesUseCases.getOthersClothesByTag(tag, pageSize, tagLastSnapshot)
            val filtered = clothesFilterManager.filterClothes(newItems)

            _tagFilteredClothes.update { it + filtered }
            tagLastSnapshot = last
            hasNextTag = last != null && filtered.isNotEmpty()
            isLoadingTag = false
        }
    }
}
