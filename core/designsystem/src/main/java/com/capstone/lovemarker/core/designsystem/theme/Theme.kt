package com.capstone.lovemarker.core.designsystem.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Stable
class LoveMarkerColorScheme(
    // red
    primary: Color,
    onPrimary: Color,
    primaryContainer: Color,
    onPrimaryContainer: Color,

    // brown
    secondary: Color,
    onSecondary: Color,
    secondaryContainer: Color,
    onSecondaryContainer: Color,

    // beige
    surface: Color,
    onSurface50: Color,
    onSurface100: Color,
    onSurface200: Color,
    onSurface300: Color,
    onSurface400: Color,
    onSurface500: Color,
    onSurface600: Color,
    onSurface700: Color,
    onSurface800: Color,
    onSurface900: Color,

    // bottom navigation bar
    surfaceVariant: Color,
    onSurfaceVariant: Color,
    surfaceContainer: Color,

    // gray
    outlineBrown: Color, // 배경이 베이지일 때
    outlineGray: Color, // 배경이 흰색일 때
    outlineVariant: Color, // divider

    // semantic
    error: Color,
    success: Color,
    inform: Color,
) {
    var primary by mutableStateOf(primary)
        private set
    var onPrimary by mutableStateOf(onPrimary)
        private set
    var primaryContainer by mutableStateOf(primaryContainer)
        private set
    var onPrimaryContainer by mutableStateOf(onPrimaryContainer)
        private set

    var secondary by mutableStateOf(secondary)
        private set
    var onSecondary by mutableStateOf(onSecondary)
        private set
    var secondaryContainer by mutableStateOf(secondaryContainer)
        private set
    var onSecondaryContainer by mutableStateOf(onSecondaryContainer)
        private set

    var surface by mutableStateOf(surface)
        private set
    var onSurface50 by mutableStateOf(onSurface50)
        private set
    var onSurface100 by mutableStateOf(onSurface100)
        private set
    var onSurface200 by mutableStateOf(onSurface200)
        private set
    var onSurface300 by mutableStateOf(onSurface300)
        private set
    var onSurface400 by mutableStateOf(onSurface400)
        private set
    var onSurface500 by mutableStateOf(onSurface500)
        private set
    var onSurface600 by mutableStateOf(onSurface600)
        private set
    var onSurface700 by mutableStateOf(onSurface700)
        private set
    var onSurface800 by mutableStateOf(onSurface800)
        private set
    var onSurface900 by mutableStateOf(onSurface900)
        private set

    var surfaceVariant by mutableStateOf(surfaceVariant)
        private set
    var onSurfaceVariant by mutableStateOf(onSurfaceVariant)
        private set
    var surfaceContainer by mutableStateOf(surfaceContainer)
        private set

    var outlineBrown by mutableStateOf(outlineBrown)
        private set
    var outlineGray by mutableStateOf(outlineGray)
        private set
    var outlineVariant by mutableStateOf(outlineVariant)
        private set

    var error by mutableStateOf(error)
        private set
    var success by mutableStateOf(success)
        private set
    var inform by mutableStateOf(inform)
        private set

    fun copy(): LoveMarkerColorScheme = LoveMarkerColorScheme(
        primary = primary,
        onPrimary = onPrimary,
        primaryContainer = primaryContainer,
        onPrimaryContainer = onPrimaryContainer,
        secondary = secondary,
        onSecondary = onSecondary,
        secondaryContainer = secondaryContainer,
        onSecondaryContainer = onSecondaryContainer,
        surface = surface,
        onSurface50 = onSurface50,
        onSurface100 = onSurface100,
        onSurface200 = onSurface200,
        onSurface300 = onSurface300,
        onSurface400 = onSurface400,
        onSurface500 = onSurface500,
        onSurface600 = onSurface600,
        onSurface700 = onSurface700,
        onSurface800 = onSurface800,
        onSurface900 = onSurface900,
        surfaceVariant = surfaceVariant,
        onSurfaceVariant = onSurfaceVariant,
        surfaceContainer = surfaceContainer,
        outlineBrown = outlineBrown,
        outlineGray = outlineGray,
        outlineVariant = outlineVariant,
        error = error,
        success = success,
        inform = inform
    )

    fun update(other: LoveMarkerColorScheme) {
        primary = other.primary
        onPrimary = other.onPrimary
        primaryContainer = other.primaryContainer
        onPrimaryContainer = other.onPrimaryContainer
        secondary = other.secondary
        onSecondary = other.onSecondary
        secondaryContainer = other.secondaryContainer
        onSecondaryContainer = other.onSecondaryContainer
        surface = other.surface
        onSurface50 = other.onSurface50
        onSurface100 = other.onSurface100
        onSurface200 = other.onSurface200
        onSurface300 = other.onSurface300
        onSurface400 = other.onSurface400
        onSurface500 = other.onSurface500
        onSurface600 = other.onSurface600
        onSurface700 = other.onSurface700
        onSurface800 = other.onSurface800
        onSurface900 = other.onSurface900
        surfaceVariant = other.surfaceVariant
        onSurfaceVariant = other.onSurfaceVariant
        surfaceContainer = other.surfaceContainer
        outlineBrown = other.outlineBrown
        outlineGray = other.outlineGray
        outlineVariant = other.outlineVariant
        error = other.error
        success = other.success
        inform = other.inform
    }
}

fun loveMarkerLightColorScheme(
    primary: Color = Red300,
    onPrimary: Color = Black,
    primaryContainer: Color = Red200,
    onPrimaryContainer: Color = Black,
    secondary: Color = Brown200,
    onSecondary: Color = Gray800,
    secondaryContainer: Color = Brown100,
    onSecondaryContainer: Color = Gray800,
    surface: Color = White,
    onSurface50: Color = Gray50,
    onSurface100: Color = Gray100,
    onSurface200: Color = Gray200,
    onSurface300: Color = Gray300,
    onSurface400: Color = Gray400,
    onSurface500: Color = Gray500,
    onSurface600: Color = Gray600,
    onSurface700: Color = Gray700,
    onSurface800: Color = Gray800,
    onSurface900: Color = Gray900,
    surfaceContainer: Color = Beige400,
    surfaceVariant: Color = Beige600,
    onSurfaceVariant: Color = Gray700,
    outlineBrown: Color = Brown800,
    outlineGray: Color = Gray400,
    outlineVariant: Color = Gray300,
    error: Color = Error,
    success: Color = Success,
    inform: Color = Inform,
) = LoveMarkerColorScheme(
    primary,
    onPrimary,
    primaryContainer,
    onPrimaryContainer,
    secondary,
    onSecondary,
    secondaryContainer,
    onSecondaryContainer,
    surface,
    onSurface50,
    onSurface100,
    onSurface200,
    onSurface300,
    onSurface400,
    onSurface500,
    onSurface600,
    onSurface700,
    onSurface800,
    onSurface900,
    surfaceVariant,
    onSurfaceVariant,
    surfaceContainer,
    outlineBrown,
    outlineGray,
    outlineVariant,
    error,
    success,
    inform
)

private val LocalLoveMarkerColorScheme = staticCompositionLocalOf<LoveMarkerColorScheme> {
    error("No LoveMarkerColors provided")
}

private val LocalLoveMarkerTypography = staticCompositionLocalOf<LoveMarkerTypography> {
    error("No LoveMarkerTypography provided")
}

// 외부에서 참조할 때 필요한 객체
object LoveMarkerTheme {
    val colorScheme: LoveMarkerColorScheme
        @Composable
        @ReadOnlyComposable
        get() = LocalLoveMarkerColorScheme.current

    val typography: LoveMarkerTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalLoveMarkerTypography.current
}

@Composable
fun ProvideLocalLoveMarkerTheme(
    colorScheme: LoveMarkerColorScheme,
    typography: LoveMarkerTypography,
    content: @Composable () -> Unit,
) {
    val provideColorScheme = remember { colorScheme.copy() }.apply { update(colorScheme) }
    val provideTypography = remember { typography.copy() }.apply { update(typography) }

    CompositionLocalProvider(
        LocalLoveMarkerColorScheme provides provideColorScheme,
        LocalLoveMarkerTypography provides provideTypography,
        content = content
    )
}

@Composable
fun LoveMarkerTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = loveMarkerLightColorScheme()
    val typography = LoveMarkerTypography()

    ProvideLocalLoveMarkerTheme(colorScheme, typography) {
        MaterialTheme(content = content)
    }
}
