package com.aube.mysize.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aube.mysize.domain.model.BodyProfile
import com.aube.mysize.domain.usecase.GetBodyProfilesUseCase
import com.aube.mysize.domain.usecase.InsertBodyProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BodyProfileViewModel @Inject constructor(
    private val insertUseCase: InsertBodyProfileUseCase,
    private val getAllUseCase: GetBodyProfilesUseCase
) : ViewModel() {

    val bodyProfiles = getAllUseCase().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    fun saveProfile(profile: BodyProfile) {
        viewModelScope.launch {
            insertUseCase(profile)
        }
    }
}
