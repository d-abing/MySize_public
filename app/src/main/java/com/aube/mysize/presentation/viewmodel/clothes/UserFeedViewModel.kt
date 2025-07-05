package com.aube.mysize.presentation.viewmodel.clothes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aube.mysize.domain.model.clothes.Clothes
import com.aube.mysize.domain.usecases.ClothesUseCases
import com.aube.mysize.domain.usecases.UserUseCases
import com.google.firebase.firestore.DocumentSnapshot
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class UserFeedViewModel @Inject constructor(
    private val clothesUseCases: ClothesUseCases,
    private val userUseCases: UserUseCases,
    private val clothesFilterManager: ClothesFilterManager
) : ViewModel() {

    private val _userNickname = MutableStateFlow("")
    val userNickname: StateFlow<String> = _userNickname

    private val _userProfileImageUrl = MutableStateFlow("")
    val userProfileImageUrl: StateFlow<String> = _userProfileImageUrl

    private val _userClothes = MutableStateFlow<List<Clothes>>(emptyList())
    val userClothes: StateFlow<List<Clothes>> = _userClothes

    private var userClothesLastSnapshot: DocumentSnapshot? = null
    private var isLoadingUserClothes = false
    private var hasNextUserClothes = true
    private val pageSize = 20

    fun loadUserFeed(userId: String) {
        viewModelScope.launch {
            val result = userUseCases.fetchUserFromFirestore(userId)
            result.onSuccess { user ->
                _userNickname.value = user.nickname
                _userProfileImageUrl.value = user.profileImageUrl.orEmpty()
                loadUserClothesPage(userId)
            }.onFailure {
                Timber.e(it, "유저 정보 로딩 실패")
            }
        }
    }

    fun loadUserClothesPage(userId: String) {
        viewModelScope.launch {
            if (isLoadingUserClothes || !hasNextUserClothes) return@launch
            isLoadingUserClothes = true

            val (newItems, last) = clothesUseCases.getUserPublicClothes(userId, pageSize, userClothesLastSnapshot)
            val filtered = clothesFilterManager.filterClothes(newItems)

            _userClothes.update { it + filtered }

            userClothesLastSnapshot = last
            hasNextUserClothes = last != null && filtered.isNotEmpty()
            isLoadingUserClothes = false
        }
    }

    fun clearUserFeedState() {
        _userNickname.value = ""
        _userProfileImageUrl.value = ""
        _userClothes.value = emptyList()
        userClothesLastSnapshot = null
        hasNextUserClothes = true

        clothesFilterManager.clearCache()
    }

    fun deleteBlockedUser(userId: String) {
        clothesFilterManager.deleteBlockedUser(userId)
    }
}
