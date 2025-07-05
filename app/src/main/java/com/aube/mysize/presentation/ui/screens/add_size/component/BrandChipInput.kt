package com.aube.mysize.presentation.ui.screens.add_size.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aube.mysize.R
import com.aube.mysize.presentation.ui.component.button.MSSmallButton
import com.aube.mysize.presentation.ui.component.chip_tap.SingleSelectableChipGroup
import com.aube.mysize.ui.theme.MySizeTheme

@Composable
fun BrandChipInput(
    brandList: List<String>,
    selectedBrand: String,
    onSelect: (String) -> Unit,
    onDelete: (String) -> Unit,
    onAddBrand: (String) -> Unit,
) {
    var newBrand by remember { mutableStateOf("") }

    Column {
        SingleSelectableChipGroup(
            options = brandList,
            selectedOption = selectedBrand,
            onSelect = onSelect,
            onDelete = onDelete
        )

        Spacer(Modifier.height(8.dp))

        Row (
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val tryAddBrand = {
                val trimmed = newBrand.trim()
                if (trimmed.isNotEmpty() && trimmed !in brandList) {
                    onAddBrand(trimmed)
                    onSelect(trimmed)
                    newBrand = ""
                }
            }

            LabeledTextField(
                value = newBrand,
                onValueChange = { newBrand = it },
                label = stringResource(R.string.placeholder_brand_add),
                modifier = Modifier.weight(1f),
                unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done,
                onDone = tryAddBrand
            )
            Spacer(Modifier.width(8.dp))

            MSSmallButton(
                text = stringResource(R.string.action_add),
                onClick = tryAddBrand
            )  
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BrandChipInputPreview() {
    MySizeTheme {
        BrandChipInput(
            brandList = listOf("Nike", "Adidas", "Puma"),
            selectedBrand = "Nike",
            onSelect = {},
            onDelete = {},
            onAddBrand = {}
        )
    }
}