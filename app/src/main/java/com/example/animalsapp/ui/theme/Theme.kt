package com.example.animalsapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val DarkBackground = Color(0xFF1A2D1A)
val DarkSurface = Color(0xFF243524)
val DarkCard = Color(0xFF2C4A2C)
val AccentYellow = Color(0xFFD4E84A)
val AccentYellowDark = Color(0xFFB8CC38)
val TextPrimary = Color(0xFFEEF2EE)
val TextSecondary = Color(0xFFB0C4B0)
val IconTint = Color(0xFF8BAF8B)

private val AppColorScheme = darkColorScheme(
    primary = AccentYellow,
    onPrimary = DarkBackground,
    background = DarkBackground,
    onBackground = TextPrimary,
    surface = DarkSurface,
    onSurface = TextPrimary,
    surfaceVariant = DarkCard,
    onSurfaceVariant = TextSecondary,
    secondary = AccentYellowDark,
    onSecondary = DarkBackground
)

@Composable
fun AnimalsAppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = AppColorScheme,
        content = content
    )
}
