package com.aube.mysize.presentation.viewmodel.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aube.mysize.domain.usecases.UserUseCases
import com.aube.mysize.presentation.model.user.UserInfo
import com.aube.mysize.presentation.model.user.toUi
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
@HiltViewModel
class UserSearchViewModel @Inject constructor(
    private val userUseCases: UserUseCases,
) : ViewModel() {

    val queryFlow = MutableStateFlow("")

    private val _searchResults = MutableStateFlow<List<UserInfo>>(emptyList())
    val searchResults: StateFlow<List<UserInfo>> = _searchResults

    private val currentUid = Firebase.auth.currentUser?.uid

    init {
        viewModelScope.launch {
            queryFlow
                .debounce(300)
                .distinctUntilChanged()
                .flatMapLatest { query ->
                    if (query.isBlank()) {
                        _searchResults.value = emptyList()
                        emptyFlow()
                    } else {
                        flow {
                            val results = userUseCases.searchUsers(query)
                                .filter { it.uid != currentUid }
                            emit(results)
                        }
                    }
                }
                .collect { _searchResults.value = it.map { user -> user.toUi() } }
        }
    }

    fun onSearch(query: String) {
        queryFlow.value = query
    }
}
