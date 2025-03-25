package com.aube.mysize.presentation.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun GenderSelector(selected: String, onSelect: (String) -> Unit) {
    Row {
        listOf("남성", "여성", "기타").forEach { option ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(end = 8.dp)
                    .clickable { onSelect(option) }
            ) {
                RadioButton(
                    selected = selected == option,
                    onClick = { onSelect(option) }
                )
                Text(option)
            }
        }
    }
}
