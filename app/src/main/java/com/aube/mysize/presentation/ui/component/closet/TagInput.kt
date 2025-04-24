package com.aube.mysize.presentation.ui.component.closet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AssistChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TagInput(
    isColorBright: Boolean,
    tags: Set<String>,
    onFocusChanged: (Boolean) -> Unit,
    onTagAdd: (String) -> Unit,
    onTagRemove: (String) -> Unit,
) {
    var input by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(8.dp)
    ) {
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            tags.forEach { tag ->
                AssistChip(
                    onClick = { onTagRemove(tag) },
                    label = { Text("#$tag", style = MaterialTheme.typography.labelMedium
                        .copy(if (isColorBright) Color.Black else Color.White),) },
                    modifier = Modifier.height(32.dp)
                )
            }

            if (tags.size < 10) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        color = if (isColorBright) Color.Black else Color.White,
                        text = "#",
                        style = MaterialTheme.typography.labelLarge
                    )

                    BasicTextField(
                        value = input,
                        onValueChange = { input = it },
                        modifier = Modifier.onFocusChanged { focusState ->
                            onFocusChanged(focusState.isFocused)
                        },
                        singleLine = true,
                        textStyle = MaterialTheme.typography.labelLarge.copy(if (isColorBright) Color.Black else Color.White),
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(onDone = {
                            if (input.isNotBlank()) {
                                onTagAdd(input)
                                input = ""
                            }
                        }),
                        decorationBox = { innerTextField ->
                            if (input.isEmpty()) {
                                Text(
                                    color = if (isColorBright) Color.DarkGray else Color.LightGray,
                                    text = "태그입력",
                                    style = MaterialTheme.typography.labelLarge.copy(color = Color.Gray)
                                )
                            }
                            innerTextField()
                        }
                    )
                }
            }
        }
    }
}



