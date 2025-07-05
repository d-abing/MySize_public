package com.aube.mysize.presentation.viewmodel.size

import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aube.mysize.domain.model.brand.Brand
import com.aube.mysize.domain.model.clothes.LinkedSizeGroup
import com.aube.mysize.domain.model.size.AccessorySize
import com.aube.mysize.domain.model.size.BottomSize
import com.aube.mysize.domain.model.size.ClothesSize
import com.aube.mysize.domain.model.size.OnePieceSize
import com.aube.mysize.domain.model.size.OuterSize
import com.aube.mysize.domain.model.size.ShoeSize
import com.aube.mysize.domain.model.size.Size
import com.aube.mysize.domain.model.size.SizeCategory
import com.aube.mysize.domain.model.size.SizeEntryType
import com.aube.mysize.domain.model.size.TopSize
import com.aube.mysize.domain.usecases.BrandUseCases
import com.aube.mysize.domain.usecases.SizeUseCases
import com.aube.mysize.presentation.model.size.toUi
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SizeViewModel @Inject constructor(
    private val sizeUseCases: SizeUseCases,
    private val brandUseCases: BrandUseCases
) : ViewModel() {

    private val _sizes = MutableStateFlow<Map<SizeCategory, List<Size>>>(emptyMap())
    val sizes: StateFlow<Map<SizeCategory, List<Size>>> = _sizes.asStateFlow()

    private val _brands = MutableStateFlow<Map<SizeCategory, List<String>>>(emptyMap())
    val brands: StateFlow<Map<SizeCategory, List<String>>> = _brands.asStateFlow()

    private val _otherSizes = mutableStateMapOf<String, Size>()

    val savedSizeIds: StateFlow<Map<SizeCategory, List<String>>> =
        sizes.map { fullMap ->
            fullMap.mapValues { (_, list) ->
                list.filterIsInstance<ClothesSize>()
                    .filter { it.entryType == SizeEntryType.SAVED }
                    .map { it.id }
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyMap())

    init {
        fetchAllSizes()
        fetchAllBrands()
    }

    fun fetchAllSizes() {
        viewModelScope.launch {
            val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return@launch
            sizeUseCases.getAllSizes(uid).collect { _sizes.value = it }
        }
    }

    private fun fetchAllBrands() {
        SizeCategory.entries.filterNot { it == SizeCategory.BODY }.forEach { category ->
            viewModelScope.launch {
                brandUseCases.getBrandListByCategory(category.toUi().label).collect { list ->
                    _brands.update { it + (category to list.map { it.name }) }
                }
            }
        }
    }

    fun insert(category: SizeCategory, size: Size, onComplete: (String) -> Unit = {}) {
        viewModelScope.launch {
            val id = sizeUseCases.insertSize(category, size)
            onComplete(id)
            fetchAllSizes()
        }
    }

    fun delete(category: SizeCategory, size: Size, linkedSizeIndex: Map<String, Set<String>>? = null) {
        viewModelScope.launch {
            val key = "${category.name}_${size.id}"
            val clothesIds = linkedSizeIndex?.getOrDefault(key, emptySet())
            sizeUseCases.deleteSize(category, size, clothesIds)
            fetchAllSizes()
        }
    }

    fun insertBrand(category: SizeCategory, name: String) {
        viewModelScope.launch {
            brandUseCases.insertBrand(Brand(name = name, category = category.toUi().label))
            fetchAllBrands()
        }
    }

    fun deleteBrand(category: SizeCategory, name: String) {
        viewModelScope.launch {
            brandUseCases.deleteBrand(Brand(name = name, category = category.toUi().label))
            fetchAllBrands()
        }
    }

    fun getSizesByLinkedGroups(linkedSizes: List<LinkedSizeGroup>, ownerUid: String): Flow<List<Size>> = flow {
        _otherSizes.clear()
        val result = mutableListOf<Size>()

        for (group in linkedSizes) {
            val category = SizeCategory.valueOf(group.category)
            for (id in group.sizeIds) {
                val size = if (ownerUid == FirebaseAuth.getInstance().currentUser?.uid) {
                    sizes.value[category]?.find { it.id == id }
                } else {
                    sizeUseCases.getSizeById(category, id, ownerUid).firstOrNull()
                }
                size?.let {
                    if (_otherSizes[id] == null) _otherSizes[id] = it
                    result.add(it)
                }
            }
        }

        emit(result)
    }

    fun getSizeById(category: SizeCategory, id: String): Size? =
        sizes.value[category]?.find { it.id == id }

    fun saveSizeById(category: SizeCategory, sizeId: String) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val size = _otherSizes[sizeId] ?: return

        val saved = when (size) {
            is TopSize -> size.copy(uid = uid, entryType = SizeEntryType.SAVED)
            is BottomSize -> size.copy(uid = uid, entryType = SizeEntryType.SAVED)
            is OuterSize -> size.copy(uid = uid, entryType = SizeEntryType.SAVED)
            is OnePieceSize -> size.copy(uid = uid, entryType = SizeEntryType.SAVED)
            is ShoeSize -> size.copy(uid = uid, entryType = SizeEntryType.SAVED)
            is AccessorySize -> size.copy(uid = uid, entryType = SizeEntryType.SAVED)
            else -> return
        }

        viewModelScope.launch {
            sizeUseCases.insertSize(category, saved)
            fetchAllSizes()
        }
    }

    fun deleteSizeById(category: SizeCategory, sizeId: String) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val size = _otherSizes[sizeId] ?: return

        val deleted = when (size) {
            is TopSize -> size.copy(uid = uid)
            is BottomSize -> size.copy(uid = uid)
            is OuterSize -> size.copy(uid = uid)
            is OnePieceSize -> size.copy(uid = uid)
            is ShoeSize -> size.copy(uid = uid)
            is AccessorySize -> size.copy(uid = uid)
            else -> return
        }

        viewModelScope.launch {
            sizeUseCases.deleteSize(category, deleted)
            fetchAllSizes()
        }
    }
}
