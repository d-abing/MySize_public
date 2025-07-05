package com.aube.mysize.presentation.ui.component.ocr

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aube.mysize.R
import com.aube.mysize.presentation.constants.VALID_LABEL_REGEX
import com.aube.mysize.presentation.ui.component.BorderColumn
import com.aube.mysize.presentation.ui.component.chip_tap.SingleSelectableChipGroup
import com.aube.mysize.ui.theme.MySizeTheme

@Composable
fun SizeLabelChip(
    mapOcrCompleted: Boolean,
    extractedLabelList: List<String>,
    selectedExtractedLabel: String,
    onSelect: (String) -> Unit
) {
    if (mapOcrCompleted && extractedLabelList.isNotEmpty()) {
        Spacer(Modifier.height(8.dp))
        BorderColumn(stringResource(R.string.ocr_select_extracted_label)) {
            SingleSelectableChipGroup(
                options = extractedLabelList.filter { it.matches(Regex(VALID_LABEL_REGEX)) },
                selectedOption = selectedExtractedLabel,
                onSelect = onSelect
            )
        }
    }
}
@Preview(showBackground = true)
@Composable
fun SizeLabelChipPreview() {
    MySizeTheme {
        SizeLabelChip(
            mapOcrCompleted = true,
            extractedLabelList = listOf("S", "M", "L", "XL", "XXL", "123_ABC", "INVALID@"),
            selectedExtractedLabel = "M",
            onSelect = {}
        )
    }
}