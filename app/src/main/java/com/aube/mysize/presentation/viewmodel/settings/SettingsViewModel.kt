package com.aube.mysize.presentation.viewmodel.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aube.mysize.data.datastore.SettingsDataStore
import com.aube.mysize.presentation.model.recommend.UserPreference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsDataStore: SettingsDataStore
) : ViewModel() {

    val language: StateFlow<String> = settingsDataStore.getLanguage()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "ko")

    val bodyFields: StateFlow<Set<String>> = settingsDataStore.getBodyFields()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptySet())

    val isBodySizeCardSticky: StateFlow<Boolean> = settingsDataStore.getIsBodySizeCardSticky()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    val isBodySizeCardExpanded: StateFlow<Boolean> = settingsDataStore.getIsBodySizeCardExpanded()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), true)

    val isBodySizeRevealed: StateFlow<Boolean> = settingsDataStore.getIsBodySizeRevealed()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), true)

    val userPreference: StateFlow<UserPreference?> = settingsDataStore.getUserPreference()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    fun saveLanguage(language: String) {
        viewModelScope.launch {
            settingsDataStore.saveLanguage(language)
        }
    }

    fun saveBodyFields(bodyFields: Set<String>) {
        viewModelScope.launch {
            settingsDataStore.saveBodyFields(bodyFields)
        }
    }

    fun saveIsBodySizeCardSticky(value: Boolean) {
        viewModelScope.launch {
            settingsDataStore.saveIsBodySizeCardSticky(value)
        }
    }

    fun saveIsBodySizeCardExpanded(value: Boolean) {
        viewModelScope.launch {
            settingsDataStore.saveIsBodySizeCardExpanded(value)
        }
    }

    fun saveIsBodySizeRevealed(value: Boolean) {
        viewModelScope.launch {
            settingsDataStore.saveIsBodySizeRevealed(value)
        }
    }

    fun saveUserPreference(preference: UserPreference) {
        viewModelScope.launch {
            settingsDataStore.saveUserPreference(preference)
        }
    }
}
