package com.aube.mysize.presentation.viewmodel.cloth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aube.mysize.domain.model.Cloth
import com.aube.mysize.domain.repository.ClothRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClothViewModel @Inject constructor(
    private val repository: ClothRepository
) : ViewModel() {

    val clothList: StateFlow<List<Cloth>> = repository.getAll()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    fun insert(cloth: Cloth) {
        viewModelScope.launch {
            repository.insert(cloth)
        }
    }

    fun delete(cloth: Cloth) {
        viewModelScope.launch {
            repository.delete(cloth)
        }
    }

    fun getById(id: Int, onResult: (Cloth?) -> Unit) {
        viewModelScope.launch {
            onResult(repository.getById(id))
        }
    }
}
