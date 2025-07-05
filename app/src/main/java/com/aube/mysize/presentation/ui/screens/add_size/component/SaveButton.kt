package com.aube.mysize.presentation.ui.screens.add_size.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aube.mysize.R

@Composable
fun BoxScope.SaveButton(
    enabled: Boolean = true,
    icon: ImageVector? = null,
    text: String? = null,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = Modifier
            .align(Alignment.BottomEnd)
            .padding(16.dp)
            .wrapContentWidth()
            .defaultMinSize(minWidth = 56.dp)
            .wrapContentHeight()
            .defaultMinSize(minHeight = 50.dp),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 6.dp,
            pressedElevation = 8.dp,
            disabledElevation = 0.dp
        ),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (enabled) Color.Black else MaterialTheme.colorScheme.surfaceVariant,
            contentColor = if (enabled) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
        ),
        contentPadding = contentPadding
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = stringResource(R.string.action_save),
                    modifier = Modifier.size(24.dp)
                )
            }
            if (!text.isNullOrEmpty()) {
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = text,
                    overflow = TextOverflow.Clip,
                    maxLines = 1,
                    fontSize = MaterialTheme.typography.labelLarge.fontSize
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SaveButtonPreview() {
    Box(modifier = Modifier.fillMaxSize()) {
        SaveButton(
            enabled = true,
            icon = Icons.Default.Add,
            text = "저장",
            onClick = {}
        )
    }
}

