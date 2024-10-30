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
    onSurface: Color,
    surfaceVariant: Color,
    onSurfaceVariant: Color,
    surfaceContainer: Color,

    // gray
    outline: Color,
    outlineVariant: Color,

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
    var onSurface by mutableStateOf(onSurface)
        private set
    var surfaceVariant by mutableStateOf(surfaceVariant)
        private set
    var onSurfaceVariant by mutableStateOf(onSurfaceVariant)
        private set
    var surfaceContainer by mutableStateOf(surfaceContainer)
        private set
    var outline by mutableStateOf(outline)
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
        onSurface = onSurface,
        surfaceVariant = surfaceVariant,
        onSurfaceVariant = onSurfaceVariant,
        surfaceContainer = surfaceContainer,
        outline = outline,
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
        onSurface = other.onSurface
        surfaceVariant = other.surfaceVariant
        onSurfaceVariant = other.onSurfaceVariant
        surfaceContainer = other.surfaceContainer
        outline = other.outline
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
    onSurface: Color = Black,
    surfaceContainer: Color = Beige400,
    surfaceVariant: Color = Beige600,
    onSurfaceVariant: Color = Gray700,
    outline: Color = Gray400,
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
    onSurface,
    surfaceVariant,
    onSurfaceVariant,
    surfaceContainer,
    outline,
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
