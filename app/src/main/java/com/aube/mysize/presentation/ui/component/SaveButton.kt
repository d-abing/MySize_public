package com.aube.mysize.presentation.ui.component

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun ColumnScope.SaveButton(onClick: () -> Unit) {
    Button(
        modifier = Modifier
            .align(Alignment.End),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Black,
            contentColor = Color.White
        ),
        onClick = onClick
    ) {
        Text(
            fontSize = MaterialTheme.typography.labelSmall.fontSize,
            text = "추가하기"
        )
    }
}