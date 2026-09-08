package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val CodeCompareDarkColorScheme = darkColorScheme(
    primary = ElectricCobalt,
    onPrimary = Color.White,
    primaryContainer = CobaltDark,
    onPrimaryContainer = CobaltLight,
    secondary = CIndigo,
    onSecondary = Color.White,
    tertiary = PythonEmerald,
    onTertiary = Color.White,
    background = MidnightBlue,
    onBackground = Slate100,
    surface = Slate800,
    onSurface = Slate100,
    surfaceVariant = Slate700,
    onSurfaceVariant = Slate300,
    outline = Slate600,
    outlineVariant = Slate700,
    error = ErrorRed,
    onError = Color.White
)

// Default immaculate high-contrast light mode with deep slate cards & crisp typography
private val CodeCompareLightColorScheme = lightColorScheme(
    primary = MidnightBlue,
    onPrimary = Color.White,
    primaryContainer = ElectricCobalt,
    onPrimaryContainer = Color.White,
    secondary = ElectricCobalt,
    onSecondary = Color.White,
    secondaryContainer = CobaltLight,
    onSecondaryContainer = CobaltDark,
    tertiary = PythonEmerald,
    onTertiary = Color.White,
    background = Slate50,
    onBackground = MidnightBlue,
    surface = SurfaceLight,
    onSurface = MidnightBlue,
    surfaceVariant = Slate100,
    onSurfaceVariant = Slate600,
    outline = Slate200,
    outlineVariant = Slate100,
    error = ErrorRed,
    onError = Color.White
)

@Composable
fun CodeCompareTheme(
    darkTheme: Boolean = false, // Defaulting to an immaculate, high-contrast light mode as specified
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) CodeCompareDarkColorScheme else CodeCompareLightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

