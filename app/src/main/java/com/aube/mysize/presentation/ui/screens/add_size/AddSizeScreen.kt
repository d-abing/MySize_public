package com.aube.mysize.presentation.ui.screens.add_size

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.aube.mysize.presentation.ui.component.addsize.SaveButton
import com.aube.mysize.presentation.ui.nav.SizeCategory
import com.aube.mysize.presentation.ui.screens.add_size.input_form.AccessorySizeInputForm
import com.aube.mysize.presentation.ui.screens.add_size.input_form.BodySizeInputForm
import com.aube.mysize.presentation.ui.screens.add_size.input_form.BottomSizeInputForm
import com.aube.mysize.presentation.ui.screens.add_size.input_form.OnePieceSizeInputForm
import com.aube.mysize.presentation.ui.screens.add_size.input_form.OuterSizeInputForm
import com.aube.mysize.presentation.ui.screens.add_size.input_form.ShoeSizeInputForm
import com.aube.mysize.presentation.ui.screens.add_size.input_form.TopSizeInputForm
import com.aube.mysize.presentation.viewmodel.size.AccessorySizeViewModel
import com.aube.mysize.presentation.viewmodel.size.BodySizeViewModel
import com.aube.mysize.presentation.viewmodel.size.BottomSizeViewModel
import com.aube.mysize.presentation.viewmodel.size.OnePieceSizeViewModel
import com.aube.mysize.presentation.viewmodel.size.OuterSizeViewModel
import com.aube.mysize.presentation.viewmodel.size.ShoeSizeViewModel
import com.aube.mysize.presentation.viewmodel.size.TopSizeViewModel

@Composable
fun AddSizeScreen(
    snackbarHostState: SnackbarHostState,
    bodyViewModel: BodySizeViewModel = hiltViewModel(),
    topViewModel: TopSizeViewModel = hiltViewModel(),
    bottomViewModel: BottomSizeViewModel = hiltViewModel(),
    outerViewModel: OuterSizeViewModel = hiltViewModel(),
    onePieceViewModel: OnePieceSizeViewModel = hiltViewModel(),
    shoeViewModel: ShoeSizeViewModel = hiltViewModel(),
    accessoryViewModel: AccessorySizeViewModel = hiltViewModel(),
    onSaved: () -> Unit
) {
    var selectedCategory by remember { mutableStateOf(SizeCategory.BODY) }

    var isMandatoryFieldsFilled by remember { mutableStateOf(false) }
    var isAllFieldsValid by remember { mutableStateOf(false) }
    var saveRequest: (() -> Unit)? = null


    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentPadding = PaddingValues(bottom = 60.dp) // 버튼 공간 확보
        ) {
            // ───── 선택 chip ─────
            item {
                LazyRow(
                    modifier = Modifier
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    items(SizeCategory.entries.toTypedArray()) { category ->
                        FilterChip(
                            selected = selectedCategory == category,
                            onClick = { selectedCategory = category },
                            label = {
                                Text(
                                    fontSize = MaterialTheme.typography.labelLarge.fontSize,
                                    text = category.label
                                )
                            },
                            shape = RoundedCornerShape(50),
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = Color.Black,
                                containerColor = MaterialTheme.colorScheme.secondary,
                                selectedLabelColor = Color.White,
                                labelColor = Color.Black ,
                            ),
                            border = null, // 🔥 border 없애기
                            modifier = Modifier
                                .height(36.dp)
                        )
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                // ───── 카테고리별 입력 UI 분기 ─────
                when (selectedCategory) {
                    SizeCategory.BODY -> BodySizeInputForm(
                        viewModel = bodyViewModel,
                        onUpdateFormState = { mandatoryFilled, allValid ->
                            isMandatoryFieldsFilled = mandatoryFilled
                            isAllFieldsValid = allValid
                        },
                        onSaved = { bodySize ->
                            saveRequest = {
                                bodyViewModel.insert(bodySize)
                                onSaved()
                            }
                        }
                    )
                    SizeCategory.TOP -> TopSizeInputForm(
                        viewModel = topViewModel,
                        snackbarHostState = snackbarHostState,
                        onUpdateFormState = { mandatoryFilled, allValid ->
                            isMandatoryFieldsFilled = mandatoryFilled
                            isAllFieldsValid = allValid
                        },
                        onSaved = { topSize ->
                            saveRequest = {
                                topViewModel.insert(topSize)
                                onSaved()
                            }
                        }
                    )

                    SizeCategory.BOTTOM -> BottomSizeInputForm(
                        viewModel = bottomViewModel,
                        snackbarHostState = snackbarHostState,
                        onUpdateFormState = { mandatoryFilled, allValid ->
                            isMandatoryFieldsFilled = mandatoryFilled
                            isAllFieldsValid = allValid
                        },
                        onSaved = { bottomSize ->
                            saveRequest = {
                                bottomViewModel.insert(bottomSize)
                                onSaved()
                            }
                        }
                    )

                    SizeCategory.OUTER -> OuterSizeInputForm(
                        viewModel = outerViewModel,
                        snackbarHostState = snackbarHostState,
                        onUpdateFormState = { mandatoryFilled, allValid ->
                            isMandatoryFieldsFilled = mandatoryFilled
                            isAllFieldsValid = allValid
                        },
                        onSaved = { outerSize ->
                            saveRequest = {
                                outerViewModel.insert(outerSize)
                                onSaved()
                            }
                        }
                    )

                    SizeCategory.ONE_PIECE -> OnePieceSizeInputForm(
                        viewModel = onePieceViewModel,
                        snackbarHostState = snackbarHostState,
                        onUpdateFormState = { mandatoryFilled, allValid ->
                            isMandatoryFieldsFilled = mandatoryFilled
                            isAllFieldsValid = allValid
                        },
                        onSaved = { onePieceSize ->
                            saveRequest = {
                                onePieceViewModel.insert(onePieceSize)
                                onSaved()
                            }
                        }
                    )

                    SizeCategory.SHOES -> ShoeSizeInputForm(
                        viewModel = shoeViewModel,
                        snackbarHostState = snackbarHostState,
                        onUpdateFormState = { mandatoryFilled, allValid ->
                            isMandatoryFieldsFilled = mandatoryFilled
                            isAllFieldsValid = allValid
                        },
                        onSaved = { shoeSize ->
                            saveRequest = {
                                shoeViewModel.insert(shoeSize)
                                onSaved()
                            }
                        }
                    )

                    SizeCategory.ACCESSORY -> AccessorySizeInputForm(
                        viewModel = accessoryViewModel,
                        onUpdateFormState = { mandatoryFilled, allValid ->
                            isMandatoryFieldsFilled = mandatoryFilled
                            isAllFieldsValid = allValid
                        },
                        onSaved = { accessorySize ->
                            saveRequest = {
                                accessoryViewModel.insert(accessorySize)
                                onSaved()
                            }
                        }
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        SaveButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(20.dp),
            enabled = isAllFieldsValid,
            elevation = ButtonDefaults.buttonElevation( // ✨ 그림자 추가
                defaultElevation = 6.dp,
                pressedElevation = 8.dp,
                disabledElevation = 0.dp
            ),
            icon = Icons.Filled.Add,
            text =
            if (!isMandatoryFieldsFilled) "필수 입력 미완료"
            else if (!isAllFieldsValid) "입력값 확인"
            else "추가",
            onClick = {
                saveRequest?.invoke()
            }
        )
    }
}