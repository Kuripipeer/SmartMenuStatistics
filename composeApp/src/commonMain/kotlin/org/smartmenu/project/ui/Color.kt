package org.smartmenu.project.ui
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val PrimaryColorLight = Color(0xFF4C8CFF)       // Azul principal
val SecondaryColorLight = Color(0xFF54D6FF)     // Aqua brillante
val BackgroundColorLight = Color(0xFFF7F9FC)    // Fondo muy claro
val SurfaceColorLight = Color(0xFFFFFFFF)       // Tarjetas blancas
val TextPrimaryLight = Color(0xFF1A1A1A)        // Texto principal
val TextSecondaryLight = Color(0xFF6C7A90)      // Texto secundario frío
val OutlineLight = Color(0xFFDCE3EE)            // Borde gris muy ligero

val AccentPurpleLight = Color(0xFFA98BFF)       // Morado (para chips / badges)

val LightColors = lightColorScheme(
    primary = PrimaryColorLight,
    secondary = SecondaryColorLight,
    background = BackgroundColorLight,
    surface = SurfaceColorLight,
    onPrimary = Color.White,
    onBackground = TextPrimaryLight,
    onSurface = TextPrimaryLight,
    surfaceVariant = Color(0xFFE8EEF7),
    onSurfaceVariant = TextSecondaryLight,
    outline = OutlineLight,
)

// ===== Dark Theme Colors =====
val PrimaryColorDark = Color(0xFF82AFFF)        // Azul claro visible en dark
val SecondaryColorDark = Color(0xFF54D6FF)      // Aqua
val BackgroundColorDark = Color(0xFF0F1113)     // Fondo oscuro azuloso
val SurfaceColorDark = Color(0xFF1A1C1F)        // Tarjetas oscuras
val TextPrimaryDark = Color(0xFFF1F5FD)         // Blanco ligeramente azulado
val TextSecondaryDark = Color(0xFFB5C2D1)       // Gris frío
val OutlineDark = Color(0xFF2C323A)             // Borde tenue
val AccentPurpleDark = Color(0xFFB9A2FF)        // Morado suave en dark

val DarkColors = darkColorScheme(
    primary = PrimaryColorDark,
    secondary = SecondaryColorDark,
    background = BackgroundColorDark,
    surface = SurfaceColorDark,
    onPrimary = Color.Black,
    onBackground = TextPrimaryDark,
    onSurface = TextPrimaryDark,
    surfaceVariant = Color(0xFF23272C),
    onSurfaceVariant = TextSecondaryDark,
    outline = OutlineDark
)
