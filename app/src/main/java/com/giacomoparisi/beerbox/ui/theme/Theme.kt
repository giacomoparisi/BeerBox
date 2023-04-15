package com.giacomoparisi.beerbox.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme =
    darkColorScheme(
        primary = YellowF2B14E,
        onPrimary = Black272524,
        secondary = Black1D262B,
        onSecondary = Gray8C9194,
        background = Black0E181C,
        onBackground = WhiteFFFFFF,
    )

@Composable
fun BeerBoxTheme(
    content: @Composable () -> Unit
) {
    // Use Only DarkColorScheme for this Example
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content
    )
}