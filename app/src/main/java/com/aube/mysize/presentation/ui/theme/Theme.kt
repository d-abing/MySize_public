package com.aube.mysize.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = PrimaryMain,
    onPrimary = Color.White,
    primaryContainer = PrimaryContainer,

    secondary = SecondaryMain,
    onSecondary = Color.Black,
    secondaryContainer = SecondaryContainer,

    tertiary = TertiaryMain,
    onTertiary = Color.Black,
    tertiaryContainer = TertiaryContainer,

    background = SurfaceColor,
    onBackground = OnBackgroundColor,
    surface = SurfaceColor,
    onSurface = OnSurfaceColor,

    error = Error
)

@Composable
fun MySizeTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}
