package com.aube.mysize.presentation.ui.screens.add_size

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.aube.mysize.presentation.ui.nav.SizeCategory
import com.aube.mysize.presentation.ui.screens.add_size.input_form.AccessorySizeInputForm
import com.aube.mysize.presentation.ui.screens.add_size.input_form.BodySizeInputForm
import com.aube.mysize.presentation.ui.screens.add_size.input_form.BottomSizeInputForm
import com.aube.mysize.presentation.ui.screens.add_size.input_form.OnePieceSizeInputForm
import com.aube.mysize.presentation.ui.screens.add_size.input_form.OuterSizeInputForm
import com.aube.mysize.presentation.ui.screens.add_size.input_form.ShoeSizeInputForm
import com.aube.mysize.presentation.ui.screens.add_size.input_form.TopSizeInputForm
import com.aube.mysize.presentation.viewmodel.AccessorySizeViewModel
import com.aube.mysize.presentation.viewmodel.BodySizeViewModel
import com.aube.mysize.presentation.viewmodel.BottomSizeViewModel
import com.aube.mysize.presentation.viewmodel.OnePieceSizeViewModel
import com.aube.mysize.presentation.viewmodel.OuterSizeViewModel
import com.aube.mysize.presentation.viewmodel.ShoeSizeViewModel
import com.aube.mysize.presentation.viewmodel.TopSizeViewModel

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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // ───── 선택 chip ─────
        LazyRow(
            modifier = Modifier
                .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            items(SizeCategory.entries.toTypedArray()) { category ->
                FilterChip(
                    selected = selectedCategory == category,
                    onClick = { selectedCategory = category },
                    shape = RoundedCornerShape(50),
                    label = {
                        Text(
                            fontSize = MaterialTheme.typography.labelMedium.fontSize,
                            text = category.label
                        )
                    },
                    modifier = Modifier
                        .height(32.dp)
                    ,
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // ───── 카테고리별 입력 UI 분기 ─────
        when (selectedCategory) {
            SizeCategory.BODY -> BodySizeInputForm(viewModel = bodyViewModel, snackbarHostState, onSaved)
            SizeCategory.TOP -> TopSizeInputForm(viewModel = topViewModel, snackbarHostState, onSaved)
            SizeCategory.BOTTOM -> BottomSizeInputForm(viewModel = bottomViewModel, snackbarHostState,  onSaved)
            SizeCategory.OUTER -> OuterSizeInputForm(viewModel = outerViewModel, snackbarHostState, onSaved)
            SizeCategory.ONE_PIECE -> OnePieceSizeInputForm(viewModel = onePieceViewModel, snackbarHostState, onSaved)
            SizeCategory.SHOES -> ShoeSizeInputForm(viewModel = shoeViewModel, snackbarHostState, onSaved)
            SizeCategory.ACCESSORY -> AccessorySizeInputForm(viewModel = accessoryViewModel, snackbarHostState, onSaved)
        }
    }
}