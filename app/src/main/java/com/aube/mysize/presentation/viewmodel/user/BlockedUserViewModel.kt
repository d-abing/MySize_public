package com.aube.mysize.presentation.viewmodel.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aube.mysize.domain.model.follow.UserSummary
import com.aube.mysize.domain.usecases.BlockingUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BlockedUserViewModel @Inject constructor(
    private val blockingUseCases: BlockingUseCases,
) : ViewModel() {

    private val _blockedUsers = MutableStateFlow<List<UserSummary>>(emptyList())
    val blockedUsers: StateFlow<List<UserSummary>> = _blockedUsers

    fun loadBlockedUserIds() {
        viewModelScope.launch {
            _blockedUsers.value = blockingUseCases.getBlockedUsers()
        }
    }

    fun blockUser(uid: String) {
        viewModelScope.launch {
            blockingUseCases.insertBlockedUser(uid)
            loadBlockedUserIds()
        }
    }

    fun unblockUser(uid: String) {
        viewModelScope.launch {
            blockingUseCases.deleteBlockedUser(uid)
            loadBlockedUserIds()
        }
    }
}
