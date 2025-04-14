package com.aube.mysize.presentation.ui.component.addsize

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun LabeledTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    unfocusedBorderColor: Color = MaterialTheme.colorScheme.outlineVariant,
    isError: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Number,
    imeAction: ImeAction = ImeAction.Next,
    onDone: () -> Unit = {},
) {
    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        value = value,
        onValueChange = { input ->
            onValueChange(input.uppercase())
        },
        isError = isError,
        textStyle = MaterialTheme.typography.labelMedium,
        label = {
            Text(
                text = label,
                fontSize = MaterialTheme.typography.labelMedium.fontSize,
                color = if (isError) Color.Black else LocalContentColor.current
            )
        },
        modifier = modifier.fillMaxWidth(),
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        keyboardActions = KeyboardActions(
            onNext = {
                if (imeAction == ImeAction.Next) {
                    focusManager.moveFocus(FocusDirection.Down)
                }
            },
            onDone = {
                if (imeAction == ImeAction.Done) {
                    focusManager.clearFocus()
                    onDone()
                }
            }
        ),
        shape = RoundedCornerShape(8.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = if (isError) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.primary, // 수정
            unfocusedBorderColor = if (isError) MaterialTheme.colorScheme.tertiary else unfocusedBorderColor, // 수정
            errorBorderColor = MaterialTheme.colorScheme.tertiary,
            cursorColor = MaterialTheme.colorScheme.primary,
            focusedLabelColor = Color.Black,
            unfocusedLabelColor = Color.Black,
            errorLabelColor = Color.Black,
            errorContainerColor = if (isError) MaterialTheme.colorScheme.tertiary.copy(alpha = 0.1f) else Color.Transparent
        )
    )
}
