package com.aube.mysize.presentation.ui.component

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun RowScope.MSIconButton(icon: ImageVector, contentDescription: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .heightIn(max = 60.dp, min = 60.dp)
            .weight(1f)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            Modifier.padding(5.dp)
        )
        Text(text = contentDescription)
    }
}