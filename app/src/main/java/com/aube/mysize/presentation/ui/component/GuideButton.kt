package com.aube.mysize.presentation.ui.component

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.HelpOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ColumnScope.GuideButton(
    text: String? = null,
    onClick: () -> Unit
) {
    TextButton (
        onClick = onClick,
        modifier = Modifier.align(Alignment.End)
    ) {
        Icon(
            modifier = Modifier.size(16.dp),
            imageVector = Icons.AutoMirrored.Filled.HelpOutline,
            contentDescription = "가이드"
        )
        text?.let {
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = text,
                fontSize = MaterialTheme.typography.labelSmall.fontSize
            )
        }
    }
}