package com.aube.mysize.presentation.ui.component.addsize

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.unit.dp

@Composable
fun SaveButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    elevation: ButtonElevation? = ButtonDefaults.buttonElevation(),
    icon: ImageVector? = null,
    text: String = "추가",
    onClick: () -> Unit,
) {
    Button(
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = if(enabled) Color.Black else MaterialTheme.colorScheme.surfaceVariant,
            contentColor = if(enabled) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
        ),
        elevation = elevation,
        onClick = onClick,
        modifier = modifier.height(50.dp)
    ) {
        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
        Text(
            fontSize = MaterialTheme.typography.labelLarge.fontSize,
            text = text
        )
    }
}