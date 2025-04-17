package com.aube.mysize.presentation.ui.component.addsize

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun SaveButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    elevation: ButtonElevation? = ButtonDefaults.buttonElevation(),
    icon: ImageVector? = null,
    text: String? = null,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .wrapContentHeight()
            .defaultMinSize(minHeight = 50.dp),
        elevation = elevation,
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
                    contentDescription = null,
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
