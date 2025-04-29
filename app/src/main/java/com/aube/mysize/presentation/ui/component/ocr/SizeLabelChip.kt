package com.aube.mysize.presentation.ui.component.ocr

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aube.mysize.presentation.ui.screens.add_size.component.BorderColumn
import com.aube.mysize.presentation.ui.screens.add_size.component.SelectableChipGroup

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
            SelectableChipGroup(
                options = extractedLabelList,
                selectedOption = selectedExtractedLabel,
                onSelect = onSelect
            )
            if (extractedLabelList.any{ it.contains("알 수 없는")}) {
                Spacer(Modifier.height(4.dp))
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    text = "사이즈 라벨이 정확히 추출되지 않아 임시 라벨을 사용합니다.\n" +
                            "임시 라벨을 선택할 시 사이즈 라벨이 정확히 기입되어 있는지 확인하세요.",
                    fontSize = MaterialTheme.typography.labelSmall.fontSize
                )
            }
        }
    }
}