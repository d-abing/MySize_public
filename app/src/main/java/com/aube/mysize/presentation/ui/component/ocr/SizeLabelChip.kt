package com.aube.mysize.presentation.ui.component.ocr

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aube.mysize.presentation.ui.screens.add_size.component.BorderColumn
import com.aube.mysize.presentation.ui.screens.add_size.component.SingleSelectableChipGroup

@Composable
fun SizeLabelChip(
    extractedSizeMap: Map<String, Map<String, String>>,
    extractedLabelList: List<String>,
    selectedExtractedLabel: String,
    onSelect: (String) -> Unit
) {
    if (extractedSizeMap.isNotEmpty()) {
        Spacer(Modifier.height(8.dp))
        BorderColumn("추출된 사이즈 선택") {
            SingleSelectableChipGroup(
                options = extractedLabelList.filter { it.matches(Regex("^[A-Z0-9/_\\- \\[\\]()]{1,10}$")) },
                selectedOption = selectedExtractedLabel,
                onSelect = onSelect
            )
        }
    }
}