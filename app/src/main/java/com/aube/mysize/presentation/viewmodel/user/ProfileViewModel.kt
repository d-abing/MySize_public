package com.aube.mysize.presentation.viewmodel.user

import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aube.mysize.app.NetworkMonitor
import com.aube.mysize.domain.model.follow.UserSummary
import com.aube.mysize.domain.usecases.BlockingUseCases
import com.aube.mysize.domain.usecases.FollowUseCases
import com.aube.mysize.domain.usecases.UserUseCases
import com.aube.mysize.presentation.model.user.UserInfo
import com.aube.mysize.presentation.model.user.toDomain
import com.aube.mysize.presentation.model.user.toUi
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userUseCases: UserUseCases,
    private val followUseCases: FollowUseCases,
    private val blockingUseCases: BlockingUseCases
) : ViewModel() {

    val user = FirebaseAuth.getInstance().currentUser
    val isConnected = NetworkMonitor.networkAvailable

    var userInfo by mutableStateOf(UserInfo())
        private set

    private val _isUploading = MutableStateFlow(false)
    val isUploading: StateFlow<Boolean> = _isUploading

    private val _followersCount = mutableStateOf(0)
    val followersCount: State<Int> = _followersCount

    private val _followingsCount = mutableStateOf(0)
    val followingsCount: State<Int> = _followingsCount

    private val _followers = MutableStateFlow<List<UserSummary>>(emptyList())
    val followers: StateFlow<List<UserSummary>> = _followers

    private val _followings = MutableStateFlow<List<UserSummary>>(emptyList())
    val followings: StateFlow<List<UserSummary>> = _followings

    private val _isProfileImageDeleting = MutableStateFlow(false)
    val isProfileImageDeleting: StateFlow<Boolean> = _isProfileImageDeleting

    init {
        loadCachedUser()
        refreshFromRemote()
    }

    private fun loadCachedUser() {
        viewModelScope.launch {
            userUseCases.getUserFromCache().collect { user ->
                user?.let { userInfo = it.toUi() }
            }
        }
    }

    private fun refreshFromRemote() {
        viewModelScope.launch {
            val uid = user?.uid ?: return@launch
            userUseCases.fetchUserFromFirestore(uid).onSuccess {
                userInfo = it.toUi()
                userUseCases.cacheUser(it)
                loadFollowCount(it.uid)
                loadFollowList(it.uid)
            }
        }
    }

    fun loadFollowCount(targetUid: String) {
        viewModelScope.launch {
            _followersCount.value = followUseCases.getFollowersCount(targetUid)
            _followingsCount.value = followUseCases.getFollowingsCount(targetUid)
        }
    }

    fun loadFollowList(userUid: String) {
        viewModelScope.launch {
            _followers.value = followUseCases.getFollowers(userUid)
            _followings.value = followUseCases.getFollowings(userUid)
        }
    }

    fun follow(targetUid: String, onFailure: ((String) -> Unit)) {
        viewModelScope.launch {
            val isBlockedByTarget = blockingUseCases.checkIfBlockedByUser(targetUid)
            if (isBlockedByTarget) {
                onFailure("상대방이 회원님을 차단하여 팔로우할 수 없습니다.")
                return@launch
            }

            followUseCases.followUser(targetUid)
            _followingsCount.value += 1

            val newFollowing = userUseCases.fetchUserFromFirestore(targetUid)
                .getOrNull()
                ?.toUi()
                ?.let { UserSummary(it.uid, it.nickname, it.profileImageUrl) }

            newFollowing?.let {
                if (_followings.value.none { user -> user.uid == targetUid }) {
                    _followings.value = _followings.value + it
                }
            }
        }
    }

    fun unfollow(targetUid: String) {
        viewModelScope.launch {
            followUseCases.unfollowUser(targetUid = targetUid)
            _followingsCount.value -= 1
            _followings.value = _followings.value.filter { it.uid != targetUid }
        }
    }

    fun removeFollower(targetUid: String) {
        viewModelScope.launch {
            followUseCases.unfollowUser(uid = targetUid, targetUid = user?.uid ?: return@launch)
            _followersCount.value -= 1
            _followers.value = _followers.value.filter { it.uid != targetUid }
        }
    }

    fun uploadProfileImage(
        imageUri: Uri,
        onMove: () -> Unit,
    ) {
        val uid = user?.uid ?: return
        val previousUser = userInfo

        _isUploading.value = true
        onMove()

        viewModelScope.launch {
            val result = userUseCases.uploadProfileImage(uid, imageUri)
            _isUploading.value = false

            result.onSuccess { imageUrl ->
                val updatedUser = userInfo.copy(profileImageUrl = imageUrl)
                userInfo = updatedUser
                userUseCases.cacheUser(updatedUser.toDomain())
            }.onFailure {
                userInfo = previousUser
                userUseCases.cacheUser(previousUser.toDomain())
            }
        }
    }

    fun deleteProfileImage() {
        val uid = user?.uid ?: return
        val previousUser = userInfo

        viewModelScope.launch {
            _isProfileImageDeleting.value = true
            val result = userUseCases.deleteProfileImage(uid)

            result.onSuccess {
                val updatedUser = userInfo.copy(profileImageUrl = "")
                userInfo = updatedUser
                userUseCases.cacheUser(updatedUser.toDomain())
            }.onFailure {
                userInfo = previousUser
                userUseCases.cacheUser(previousUser.toDomain())
            }
            _isProfileImageDeleting.value = false
        }
    }

    fun updateNickname(nickname: String, onMove: () -> Unit) {
        val uid = user?.uid ?: return

        val prevUser = userInfo
        val updatedUser = userInfo.copy(nickname = nickname)
        userInfo = updatedUser

        viewModelScope.launch {
            userUseCases.cacheUser(updatedUser.toDomain())
            onMove()

            val result = userUseCases.updateNickname(uid, nickname)

            if (result.isFailure) {
                userInfo = prevUser
                userUseCases.cacheUser(prevUser.toDomain())
            }
        }
    }
}