package com.aube.mysize.presentation.ui.component.ocr

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ColumnScope.SizeOcrGuide(
    onClick: () -> Unit
) {
    TextButton (
        onClick = onClick,
        modifier = Modifier.align(Alignment.End)
    ) {
        Icon(Icons.Default.HelpOutline, contentDescription = "가이드")
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = "캡쳐화면 가이드 보기",
            fontSize = MaterialTheme.typography.labelSmall.fontSize
        )
    }
}