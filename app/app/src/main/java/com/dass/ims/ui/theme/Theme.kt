package com.dass.ims.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

private val LightColors = lightColorScheme(
    primary = ImsColors().accent,
    onPrimary = ImsColors().bg,
    background = ImsColors().bg,
    onBackground = ImsColors().text,
    surface = ImsColors().surface,
    onSurface = ImsColors().text,
    outline = ImsColors().border,
)

@Composable
fun ImsTheme(content: @Composable () -> Unit) {
    val colors = ImsColors()
    CompositionLocalProvider(LocalImsColors provides colors) {
        MaterialTheme(
            colorScheme = LightColors,
            typography = ImsTypography,
            content = content,
        )
    }
}

object ImsTheme {
    val colors: ImsColors
        @Composable get() = LocalImsColors.current
}
