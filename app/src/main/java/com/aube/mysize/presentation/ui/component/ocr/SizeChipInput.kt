package com.aube.mysize.presentation.ui.component.ocr

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aube.mysize.presentation.ui.component.BorderColumn
import com.aube.mysize.presentation.ui.component.SelectableChipGroup

@Composable
fun SizeChipInput(
    extractedSizeMap: Map<String, Map<String, String>>,
    extractedLabelList: List<String>,
    selectedExtractedLabel: String,
    onSelect: (String) -> Unit
) {
    if (extractedSizeMap.isNotEmpty()) {
        Spacer(Modifier.height(8.dp))
        BorderColumn("추출된 사이즈 선택") {
            SelectableChipGroup(
                options = extractedLabelList,
                selectedOption = selectedExtractedLabel,
                onSelect = onSelect
            )
        }
    }
}