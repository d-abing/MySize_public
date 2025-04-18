package com.aube.mysize.presentation.viewmodel.clothes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aube.mysize.domain.model.Clothes
import com.aube.mysize.domain.repository.ClothesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClothesViewModel @Inject constructor(
    private val repository: ClothesRepository
) : ViewModel() {

    val clothesList: StateFlow<List<Clothes>> = repository.getAll()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    fun insert(clothes: Clothes) {
        viewModelScope.launch {
            repository.insert(clothes)
        }
    }

    fun delete(clothes: Clothes) {
        viewModelScope.launch {
            repository.delete(clothes)
        }
    }

    fun getById(id: Int, onResult: (Clothes?) -> Unit) {
        viewModelScope.launch {
            onResult(repository.getById(id))
        }
    }
}
