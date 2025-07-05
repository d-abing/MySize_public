package com.aube.mysize.presentation.ui.component.button

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aube.mysize.ui.theme.MySizeTheme

@Composable
fun MSSelectOptionButton(
    text: String,
    icon: ImageVector,
    selected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = if (selected)
        MaterialTheme.colorScheme.secondary
    else
        MaterialTheme.colorScheme.background

    val textColor = if (selected)
        MaterialTheme.colorScheme.onSecondary
    else
        MaterialTheme.colorScheme.onBackground

    Surface(
        modifier = Modifier
            .padding(end = 8.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() },
        color = backgroundColor,
        tonalElevation = if (selected) 2.dp else 0.dp
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = text,
                tint = textColor,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.labelLarge,
                color = textColor
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MSSelectOptionButtonPreview() {
    MySizeTheme {
        Row(modifier = Modifier.padding(16.dp)) {
            MSSelectOptionButton(
                text = "전체",
                icon = Icons.Default.Check,
                selected = true,
                onClick = {}
            )
            MSSelectOptionButton(
                text = "선택 안됨",
                icon = Icons.Default.Check,
                selected = false,
                onClick = {}
            )
        }
    }
}