package com.aube.mysize.presentation.ui.component.chip_tap

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aube.mysize.R
import com.aube.mysize.presentation.constants.OTHER_BRAND
import com.aube.mysize.ui.theme.MySizeTheme

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun <T : Any> SingleSelectableChipGroup(
    options: List<T>,
    selectedOption: T?,
    onSelect: (T) -> Unit,
    optionTextSelector: (T) -> String = { it.toString() },
    onDelete: ((T) -> Unit)? = null,
) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        options.forEach { option ->
            val label = optionTextSelector(option)
            FilterChip(
                selected = selectedOption == option,
                onClick = { onSelect(option) },
                label = {
                    Text(
                        text = label,
                        fontSize = MaterialTheme.typography.labelMedium.fontSize
                    )
                },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = Color(0xFFE0E0E0),
                    selectedLabelColor = Color.DarkGray,
                    containerColor = Color.White,
                    labelColor = Color.DarkGray
                ),
                modifier = Modifier
                    .heightIn(min = 28.dp, max = 36.dp),
                trailingIcon = {
                    if (onDelete != null && label != OTHER_BRAND) {
                        Spacer(Modifier.width(20.dp))
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = stringResource(R.string.action_delete),
                            tint = Color.DarkGray,
                            modifier = Modifier
                                .clickable { onDelete(option) }
                                .size(10.dp)
                        )
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SingleSelectableChipGroupPreview() {
    MySizeTheme {
        var selected by remember { mutableStateOf("M") }

        SingleSelectableChipGroup(
            options = listOf("S", "M", "L", "XL", "Other"),
            selectedOption = selected,
            onSelect = { selected = it },
            onDelete = { /* 삭제 로직 */ }
        )
    }
}
