package com.aube.mysize.presentation.ui.component

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun RowScope.SaveButton(enabled: Boolean = true, text: String = "추가하기", onClick: () -> Unit) {
    Button(
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Black,
            contentColor = Color.White
        ),
        onClick = onClick,
        modifier = Modifier.align(Alignment.CenterVertically)
    ) {
        Text(
            fontSize = MaterialTheme.typography.labelSmall.fontSize,
            text = text
        )
    }
}