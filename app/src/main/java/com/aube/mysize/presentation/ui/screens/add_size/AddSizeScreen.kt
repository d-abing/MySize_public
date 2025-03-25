package com.aube.mysize.presentation.ui.screens.add_size

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
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
import com.aube.mysize.presentation.ui.screens.add_size.input_form.OuterSizeInputForm
import com.aube.mysize.presentation.ui.screens.add_size.input_form.ShoeSizeInputForm
import com.aube.mysize.presentation.ui.screens.add_size.input_form.TopSizeInputForm
import com.aube.mysize.presentation.viewmodel.AccessorySizeViewModel
import com.aube.mysize.presentation.viewmodel.BodySizeViewModel
import com.aube.mysize.presentation.viewmodel.BottomSizeViewModel
import com.aube.mysize.presentation.viewmodel.OuterSizeViewModel
import com.aube.mysize.presentation.viewmodel.ShoeSizeViewModel
import com.aube.mysize.presentation.viewmodel.TopSizeViewModel

@Composable
fun AddSizeScreen(
    bodyViewModel: BodySizeViewModel = hiltViewModel(),
    topViewModel: TopSizeViewModel = hiltViewModel(),
    bottomViewModel: BottomSizeViewModel = hiltViewModel(),
    outerViewModel: OuterSizeViewModel = hiltViewModel(),
    shoeViewModel: ShoeSizeViewModel = hiltViewModel(),
    accessoryViewModel: AccessorySizeViewModel = hiltViewModel(),
    onSaved: () -> Unit
) {
    var selectedCategory by remember { mutableStateOf(SizeCategory.BODY) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // ───── 선택 탭 or 드롭다운 ─────
        Text("사이즈 종류 선택", style = MaterialTheme.typography.titleMedium)
        Row {
            SizeCategory.values().forEach { category ->
                Text(
                    text = category.label,
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .clickable { selectedCategory = category }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // ───── 카테고리별 입력 UI 분기 ─────
        when (selectedCategory) {
            SizeCategory.BODY -> BodySizeInputForm(viewModel = bodyViewModel, onSaved)
            SizeCategory.TOP -> TopSizeInputForm(viewModel = topViewModel, onSaved)
            SizeCategory.BOTTOM -> BottomSizeInputForm(viewModel = bottomViewModel, onSaved)
            SizeCategory.OUTER -> OuterSizeInputForm(viewModel = outerViewModel, onSaved)
            SizeCategory.SHOES -> ShoeSizeInputForm(viewModel = shoeViewModel, onSaved)
            SizeCategory.ACCESSORY -> AccessorySizeInputForm(viewModel = accessoryViewModel, onSaved)
        }
    }
}