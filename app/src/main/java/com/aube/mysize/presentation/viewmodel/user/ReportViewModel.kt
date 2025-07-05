package com.aube.mysize.presentation.viewmodel.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aube.mysize.domain.usecases.ReportUseCases
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
    private val reportUseCases: ReportUseCases
) : ViewModel() {

    private val reporterId = FirebaseAuth.getInstance().currentUser?.uid
        ?: throw IllegalStateException("로그인된 사용자 없음")

    fun reportClothes(clothesId: String, reason: String) {
        viewModelScope.launch {
            reportUseCases.submitReport(
                reporterId = reporterId,
                targetType = "clothes",
                targetId = clothesId,
                reason = reason
            )
        }
    }

    fun reportUser(userId: String, reason: String) {
        viewModelScope.launch {
            reportUseCases.submitReport(
                reporterId = reporterId,
                targetType = "user",
                targetId = userId,
                reason = reason
            )
        }
    }
}
