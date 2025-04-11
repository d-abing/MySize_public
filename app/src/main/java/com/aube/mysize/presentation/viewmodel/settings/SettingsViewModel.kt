package com.aube.mysize.presentation.viewmodel.settings

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aube.mysize.presentation.ui.datastore.SettingsDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _selectedLanguage = MutableStateFlow("ko")
    val selectedLanguage: StateFlow<String> = _selectedLanguage

    init {
        viewModelScope.launch {
            SettingsDataStore.getLanguage(context)
                .collect { lang ->
                    _selectedLanguage.value = lang
                }
        }
    }

    fun updateLanguage(language: String) {
        viewModelScope.launch {
            SettingsDataStore.saveLanguage(context, language)
            _selectedLanguage.value = language
        }
    }
}
