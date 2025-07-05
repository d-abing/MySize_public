import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ModeEdit
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.aube.mysize.R
import com.aube.mysize.domain.model.size.AccessorySize
import com.aube.mysize.domain.model.size.BodySize
import com.aube.mysize.domain.model.size.BottomSize
import com.aube.mysize.domain.model.size.OnePieceSize
import com.aube.mysize.domain.model.size.OuterSize
import com.aube.mysize.domain.model.size.ShoeSize
import com.aube.mysize.domain.model.size.SizeCategory
import com.aube.mysize.domain.model.size.TopSize
import com.aube.mysize.presentation.ui.component.chip_tap.MSCategoryChip
import com.aube.mysize.presentation.ui.screens.add_size.component.SaveButton
import com.aube.mysize.presentation.ui.screens.add_size.input_form.AccessorySizeInputForm
import com.aube.mysize.presentation.ui.screens.add_size.input_form.BodySizeInputForm
import com.aube.mysize.presentation.ui.screens.add_size.input_form.BottomSizeInputForm
import com.aube.mysize.presentation.ui.screens.add_size.input_form.OnePieceSizeInputForm
import com.aube.mysize.presentation.ui.screens.add_size.input_form.OuterSizeInputForm
import com.aube.mysize.presentation.ui.screens.add_size.input_form.ShoeSizeInputForm
import com.aube.mysize.presentation.ui.screens.add_size.input_form.TopSizeInputForm
import com.aube.mysize.presentation.viewmodel.size.SizeViewModel

@Composable
fun AddSizeScreen(
    sizeViewModel: SizeViewModel,
    navController: NavController,
    backStackEntry: NavBackStackEntry,
    snackbarHostState: SnackbarHostState,
    onNavigateToMySizeScreen: () -> Unit
) {
    var isMandatoryFieldsFilled by remember { mutableStateOf(false) }
    var isAllFieldsValid by remember { mutableStateOf(false) }
    var saveRequest: (() -> Unit)? = null

    val listState = rememberLazyListState()

    val routeCategory = backStackEntry.arguments?.getString("category") ?: "BODY"
    val allCategories = remember { SizeCategory.entries.toList() }
    val categories = remember(routeCategory) {
        when (routeCategory) {
            "ADDBODY" -> allCategories.filter { it == SizeCategory.BODY }
            "BODY" -> allCategories
            else -> allCategories.filter { it != SizeCategory.BODY }
        }
    }
    var selectedCategory by remember { mutableStateOf(SizeCategory.BODY) }

    val id = backStackEntry.arguments?.getString("id") ?: "No Id"
    val hasExistingId = id != "No Id"

    BackHandler {
        navController.previousBackStackEntry?.savedStateHandle?.set("new_size_id", "No Id")
        navController.popBackStack()
    }

    LaunchedEffect(Unit) {
        if (routeCategory != "ADDBODY") selectedCategory = SizeCategory.valueOf(routeCategory)
    }

    val isAtBottom by remember {
        derivedStateOf {
            val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()
            val totalItems = listState.layoutInfo.totalItemsCount
            lastVisibleItem != null && lastVisibleItem.index == totalItems - 1
        }
    }

    val brandMap by sizeViewModel.brands.collectAsState()
    val brands = remember(brandMap) {
        mapOf(
            SizeCategory.TOP to brandMap[SizeCategory.TOP].orEmpty(),
            SizeCategory.BOTTOM to brandMap[SizeCategory.BOTTOM].orEmpty(),
            SizeCategory.OUTER to brandMap[SizeCategory.OUTER].orEmpty(),
            SizeCategory.ONE_PIECE to brandMap[SizeCategory.ONE_PIECE].orEmpty(),
            SizeCategory.SHOE to brandMap[SizeCategory.SHOE].orEmpty(),
            SizeCategory.ACCESSORY to brandMap[SizeCategory.ACCESSORY].orEmpty()
        )
    }
    val bodyInitial = remember { sizeViewModel.getSizeById(SizeCategory.BODY, id) as? BodySize }
    val topInitial = remember { sizeViewModel.getSizeById(SizeCategory.TOP, id) as? TopSize }
    val bottomInitial = remember { sizeViewModel.getSizeById(SizeCategory.BOTTOM, id) as? BottomSize }
    val outerInitial = remember { sizeViewModel.getSizeById(SizeCategory.OUTER, id) as? OuterSize }
    val onePieceInitial = remember { sizeViewModel.getSizeById(SizeCategory.ONE_PIECE, id) as? OnePieceSize }
    val shoeInitial = remember { sizeViewModel.getSizeById(SizeCategory.SHOE, id) as? ShoeSize }
    val accessoryInitial = remember { sizeViewModel.getSizeById(SizeCategory.ACCESSORY, id) as? AccessorySize }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(top = 8.dp),
            state = listState,
        ) {
            item {
                MSCategoryChip(
                    categories = categories,
                    selectedCategory = selectedCategory,
                    onClick = { category -> selectedCategory = category }
                )
            }

            item { Spacer(modifier = Modifier.height(8.dp)) }

            item {
                when (selectedCategory) {
                    SizeCategory.BODY -> BodySizeInputForm(
                        initialSize = bodyInitial,
                        onUpdateFormState = { m, v -> isMandatoryFieldsFilled = m; isAllFieldsValid = v },
                        onSaved = { size ->
                            saveRequest = {
                                val final = if (hasExistingId) size.copy(id = id) else size
                                sizeViewModel.insert(SizeCategory.BODY, final) {
                                    if (routeCategory != "BODY") navController.popBackStack()
                                    else onNavigateToMySizeScreen()
                                }
                            }
                        }
                    )

                    SizeCategory.TOP -> TopSizeInputForm(
                        initialSize = topInitial,
                        brandList = brands[SizeCategory.TOP].orEmpty(),
                        snackbarHostState = snackbarHostState,
                        onAddBrand = { sizeViewModel.insertBrand(SizeCategory.TOP, it) },
                        onDeleteBrand = { sizeViewModel.deleteBrand(SizeCategory.TOP, it) },
                        onUpdateFormState = { m, v -> isMandatoryFieldsFilled = m; isAllFieldsValid = v },
                        onSaved = { size ->
                            saveRequest = {
                                val final = if (hasExistingId) size.copy(id = id) else size
                                sizeViewModel.insert(SizeCategory.TOP, final) {
                                    handleResult(it, navController, routeCategory, selectedCategory, onNavigateToMySizeScreen)
                                }
                            }
                        }
                    )

                    SizeCategory.BOTTOM -> BottomSizeInputForm(
                        initialSize = bottomInitial,
                        brandList = brands[SizeCategory.BOTTOM].orEmpty(),
                        snackbarHostState = snackbarHostState,
                        onAddBrand = { sizeViewModel.insertBrand(SizeCategory.BOTTOM, it) },
                        onDeleteBrand = { sizeViewModel.deleteBrand(SizeCategory.BOTTOM, it) },
                        onUpdateFormState = { m, v -> isMandatoryFieldsFilled = m; isAllFieldsValid = v },
                        onSaved = { size ->
                            saveRequest = {
                                val final = if (hasExistingId) size.copy(id = id) else size
                                sizeViewModel.insert(SizeCategory.BOTTOM, final) {
                                    handleResult(it, navController, routeCategory, selectedCategory, onNavigateToMySizeScreen)
                                }
                            }
                        }
                    )

                    SizeCategory.OUTER -> OuterSizeInputForm(
                        initialSize = outerInitial,
                        brandList = brands[SizeCategory.OUTER].orEmpty(),
                        snackbarHostState = snackbarHostState,
                        onAddBrand = { sizeViewModel.insertBrand(SizeCategory.OUTER, it) },
                        onDeleteBrand = { sizeViewModel.deleteBrand(SizeCategory.OUTER, it) },
                        onUpdateFormState = { m, v -> isMandatoryFieldsFilled = m; isAllFieldsValid = v },
                        onSaved = { size ->
                            saveRequest = {
                                val final = if (hasExistingId) size.copy(id = id) else size
                                sizeViewModel.insert(SizeCategory.OUTER, final) {
                                    handleResult(it, navController, routeCategory, selectedCategory, onNavigateToMySizeScreen)
                                }
                            }
                        }
                    )

                    SizeCategory.ONE_PIECE -> OnePieceSizeInputForm(
                        initialSize = onePieceInitial,
                        brandList = brands[SizeCategory.ONE_PIECE].orEmpty(),
                        snackbarHostState = snackbarHostState,
                        onAddBrand = { sizeViewModel.insertBrand(SizeCategory.ONE_PIECE, it) },
                        onDeleteBrand = { sizeViewModel.deleteBrand(SizeCategory.ONE_PIECE, it) },
                        onUpdateFormState = { m, v -> isMandatoryFieldsFilled = m; isAllFieldsValid = v },
                        onSaved = { size ->
                            saveRequest = {
                                val final = if (hasExistingId) size.copy(id = id) else size
                                sizeViewModel.insert(SizeCategory.ONE_PIECE, final) {
                                    handleResult(it, navController, routeCategory, selectedCategory, onNavigateToMySizeScreen)
                                }
                            }
                        }
                    )

                    SizeCategory.SHOE -> ShoeSizeInputForm(
                        initialSize = shoeInitial,
                        brandList = brands[SizeCategory.SHOE].orEmpty(),
                        snackbarHostState = snackbarHostState,
                        onAddBrand = { sizeViewModel.insertBrand(SizeCategory.SHOE, it) },
                        onDeleteBrand = { sizeViewModel.deleteBrand(SizeCategory.SHOE, it) },
                        onUpdateFormState = { m, v -> isMandatoryFieldsFilled = m; isAllFieldsValid = v },
                        onSaved = { size ->
                            saveRequest = {
                                val final = if (hasExistingId) size.copy(id = id) else size
                                sizeViewModel.insert(SizeCategory.SHOE, final) {
                                    handleResult(it, navController, routeCategory, selectedCategory, onNavigateToMySizeScreen)
                                }
                            }
                        }
                    )

                    SizeCategory.ACCESSORY -> AccessorySizeInputForm(
                        initialSize = accessoryInitial,
                        brandList = brands[SizeCategory.ACCESSORY].orEmpty(),
                        onAddBrand = { sizeViewModel.insertBrand(SizeCategory.ACCESSORY, it) },
                        onDeleteBrand = { sizeViewModel.deleteBrand(SizeCategory.ACCESSORY, it) },
                        onUpdateFormState = { m, v -> isMandatoryFieldsFilled = m; isAllFieldsValid = v },
                        onSaved = { size ->
                            saveRequest = {
                                val final = if (hasExistingId) size.copy(id = id) else size
                                sizeViewModel.insert(SizeCategory.ACCESSORY, final) {
                                    handleResult(it, navController, routeCategory, selectedCategory, onNavigateToMySizeScreen)
                                }
                            }
                        }
                    )
                }
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }
        }

        val animatedPadding by animateDpAsState(
            targetValue = if (isAtBottom) 12.dp else 20.dp,
            label = "ButtonHorizontalPadding"
        )

        SaveButton(
            enabled = isAllFieldsValid,
            icon = if (hasExistingId) Icons.Filled.ModeEdit else Icons.Filled.Add,
            text = if (isAtBottom) null else when {
                !isMandatoryFieldsFilled -> stringResource(R.string.error_required_fields_incomplete)
                !isAllFieldsValid -> stringResource(R.string.error_check_input)
                hasExistingId -> stringResource(R.string.action_edit)
                else -> stringResource(R.string.action_add)
            },
            contentPadding = PaddingValues(horizontal = animatedPadding, vertical = 14.dp),
            onClick = { saveRequest?.invoke() }
        )
    }
}

private fun handleResult(
    newId: String?,
    navController: NavController,
    routeCategory: String,
    selectedCategory: SizeCategory,
    onNavigateToMySizeScreen: () -> Unit
) {
    val handle = navController.previousBackStackEntry?.savedStateHandle
    if (routeCategory != "BODY") {
        handle?.set("new_size_id", newId ?: "No Id")
        handle?.set("new_size_category", selectedCategory)
        navController.popBackStack()
    } else {
        onNavigateToMySizeScreen()
    }
}
