package com.example.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = KeratonGoldLight,
    onPrimary = Color.Black,
    primaryContainer = SoganPrimary,
    onPrimaryContainer = Color.White,
    secondary = KeratonGold,
    onSecondary = Color.Black,
    secondaryContainer = SoganMedium,
    tertiary = KencanaAmber,
    background = DarkBackground,
    surface = DarkSurface,
    surfaceVariant = DarkSurfaceVariant,
    onBackground = Color(0xFFF5F5F4),
    onSurface = Color(0xFFF5F5F4)
)

private val LightColorScheme = lightColorScheme(
    primary = SoganPrimary,
    onPrimary = Color.White,
    primaryContainer = KeratonGoldContainer,
    onPrimaryContainer = SoganDark,
    secondary = KeratonGold,
    onSecondary = Color.Black,
    secondaryContainer = KeratonGoldContainer,
    onSecondaryContainer = SoganDark,
    tertiary = BataMerah,
    background = KremJawa,
    surface = KremSurface,
    surfaceVariant = KremSurfaceVariant,
    onBackground = Color(0xFF292524),
    onSurface = Color(0xFF292524)
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Keep authentic Javanese Keraton Sogan palette
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as? Activity)?.window
            if (window != null) {
                val insetsController = WindowCompat.getInsetsController(window, view)
                // Opsi A: Force white status bar icons for dark royal batik banner
                insetsController.isAppearanceLightStatusBars = false
                insetsController.isAppearanceLightNavigationBars = !darkTheme
            }
        }
    }

    MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
}
