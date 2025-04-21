package com.aube.mysize.presentation.ui.component.addsize

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

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
        SelectableChipGroup(
            options = brandList,
            selectedOption = selectedBrand,
            onSelect = onSelect,
            onDelete = onDelete,
        )

        Spacer(Modifier.height(8.dp))

        Row (
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            LabeledTextField(
                value = newBrand,
                onValueChange = { newBrand = it },
                label = "브랜드 추가",
                modifier = Modifier.weight(1f),
                unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done,
                onDone = {
                    val trimmed = newBrand.trim()
                    if (trimmed.isNotEmpty() && trimmed !in brandList) {
                        onAddBrand(trimmed)
                        onSelect(trimmed)
                        newBrand = ""
                    }
                }
            )
            Spacer(Modifier.width(8.dp))

            Button(
                onClick = {
                    val trimmed = newBrand.trim()
                    if (trimmed.isNotEmpty() && trimmed !in brandList) {
                        onAddBrand(trimmed)
                        onSelect(trimmed)
                        newBrand = ""
                    }
                },
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(top = 8.dp),
            ) {
                Text("추가")
            }
        }
    }
}
