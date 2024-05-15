package com.example.drutask.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable

/**
 * will behave as our theme source
 */
object DruTheme {
    val colors
        @Composable
        @ReadOnlyComposable
        get() = localDruColors(isSystemInDarkTheme()).current

    val typography
        @Composable
        @ReadOnlyComposable
        get() = LocalDruTypography.current

    val sizing
        @Composable
        @ReadOnlyComposable
        get() = LocalDruSizing.current

    val spacing
        @Composable
        @ReadOnlyComposable
        get() = LocalDruSpacing.current
}

@Composable
fun DruComposeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {

    if (darkTheme)
        MaterialTheme(
            darkColorPalette,
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    else
        MaterialTheme(
            colors = LightColorPalette,
            typography = Typography,
            shapes = Shapes,
            content = content
        )

}
