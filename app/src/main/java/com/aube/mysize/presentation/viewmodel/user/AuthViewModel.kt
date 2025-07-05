package com.aube.mysize.presentation.viewmodel.user

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aube.mysize.domain.usecases.UserUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val userUseCases: UserUseCases
) : ViewModel() {

    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var nickname by mutableStateOf("")
    var isLoading by mutableStateOf(false)

    private val _isDeleting = MutableStateFlow(false)
    val isDeleting: StateFlow<Boolean> = _isDeleting

    private val _isChanging = MutableStateFlow(false)
    val isChanging: StateFlow<Boolean> = _isChanging

    fun signInWithEmail(context: Context, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        if (email.isBlank() || password.isBlank()) return

        viewModelScope.launch {
            isLoading = true
            try {
                val result = userUseCases.signIn.withEmail(email, password)
                result.onSuccess {
                    onSuccess()
                }.onFailure {
                    onFailure(AuthErrorMessageMapper.fromSignIn(it, context))
                }
            } finally {
                isLoading = false
            }
        }
    }

    fun signInWithGoogle(context: Context, idToken: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        viewModelScope.launch {
            isLoading = true
            try {
                val result = userUseCases.signIn.withGoogle(idToken)
                result.onSuccess { user ->
                    Result.runCatching {
                        val email = user.email.orEmpty()
                        val nickname = user.displayName ?: "사용자"
                        val photoUrl = user.photoUrl?.toString()
                        userUseCases.persistUserToFirestore(user.uid, email, nickname, photoUrl)
                    }.onSuccess {
                        onSuccess()
                    }.onFailure { e ->
                        onFailure("Firestore 저장 실패: ${e.message}")
                    }
                }.onFailure {
                    onFailure(AuthErrorMessageMapper.fromSignIn(it, context))
                }
            } finally {
                isLoading = false
            }
        }
    }

    fun signOut(context: Context, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        viewModelScope.launch {
            val result = userUseCases.signOut()
            result.onSuccess {
                onSuccess()
            }.onFailure {
                onFailure(AuthErrorMessageMapper.fromSignOut(it, context))
            }
        }
    }

    fun signUpWithEmail(context: Context, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        if (email.isBlank() || password.isBlank() || nickname.isBlank()) return

        viewModelScope.launch {
            isLoading = true
            try {
                val result = userUseCases.signUp(email, password, nickname)
                result.onSuccess { user ->
                    Result.runCatching {
                        userUseCases.persistUserToFirestore(user.uid, email, nickname, null)
                    }.onSuccess {
                        onSuccess()
                    }.onFailure {
                        onFailure("Firestore 저장 실패: ${it.message}")
                    }
                }.onFailure {
                    onFailure(AuthErrorMessageMapper.fromSignUp(it, context))
                }
            } finally {
                isLoading = false
            }
        }
    }

    fun sendEmailVerification(onSent: () -> Unit, onFailure: (String) -> Unit) {
        viewModelScope.launch {
            userUseCases.sendEmailVerification()
                .onSuccess { onSent() }
                .onFailure { onFailure(it.message ?: "이메일 인증 전송 실패") }
        }
    }

    fun checkEmailVerified(onVerified: () -> Unit, onNotVerified: () -> Unit, onFailure: (String) -> Unit) {
        viewModelScope.launch {
            userUseCases.checkEmailVerified()
                .onSuccess { isVerified ->
                    if (isVerified) onVerified() else onNotVerified()
                }.onFailure {
                    onFailure(it.message ?: "이메일 인증 확인 실패")
                }
        }
    }

    fun changePassword(context: Context, current: String, new: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        viewModelScope.launch {
            _isChanging.value = true
            val result = userUseCases.changePassword(current, new)
            result.onSuccess { onSuccess() }
                .onFailure { onFailure(AuthErrorMessageMapper.fromChangePassword(it, context)) }
            _isChanging.value = false
        }
    }

    fun deleteEmailUser(context: Context, password: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        viewModelScope.launch {
            _isDeleting.value = true
            try {
                val result = userUseCases.deleteUser.deleteWithEmail(password)
                result.onSuccess { onSuccess() }
                    .onFailure { onFailure(AuthErrorMessageMapper.fromDeleteUser(it, context)) }
            } finally {
                _isDeleting.value = false
            }
        }
    }

    fun deleteGoogleUser(context: Context, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        viewModelScope.launch {
            _isDeleting.value = true
            try {
                val result = userUseCases.deleteUser.deleteWithGoogle()
                result.onSuccess { onSuccess() }
                    .onFailure { onFailure(AuthErrorMessageMapper.fromDeleteUser(it, context)) }
            } finally {
                _isDeleting.value = false
            }
        }
    }
}
