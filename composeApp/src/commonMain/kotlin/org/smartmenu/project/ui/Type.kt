package org.smartmenu.project.ui

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import org.jetbrains.compose.resources.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import smartmenustatistics.composeapp.generated.resources.Res
import smartmenustatistics.composeapp.generated.resources.poppins_black
import smartmenustatistics.composeapp.generated.resources.poppins_bold
import smartmenustatistics.composeapp.generated.resources.poppins_light
import smartmenustatistics.composeapp.generated.resources.poppins_regular

@Composable
fun PoppinsFontFamily() = FontFamily(
    Font(Res.font.poppins_regular, weight = FontWeight.W300),
    Font(Res.font.poppins_bold, weight = FontWeight.W700),
    Font(Res.font.poppins_light, weight = FontWeight.W500),
    Font(Res.font.poppins_black, weight = FontWeight.W900),
)

@Composable
fun PoppinsTypo() : Typography{
    val poppins = PoppinsFontFamily()
    return Typography(
        displayLarge = TextStyle(
            fontWeight = FontWeight.W300,
            fontFamily = poppins,
            fontSize = 57.sp,
            lineHeight = 64.sp,
            letterSpacing = (-0.25).sp
        ),
        displayMedium = TextStyle(
            fontWeight = FontWeight.W300,
            fontFamily = poppins,
            fontSize = 45.sp,
            lineHeight = 52.sp
        ),
        displaySmall = TextStyle(
            fontWeight = FontWeight.Normal,
            fontFamily = poppins,
            fontSize = 36.sp,
            lineHeight = 44.sp
        ),

        headlineLarge = TextStyle(
            fontWeight = FontWeight.Normal,
            fontFamily = poppins,
            fontSize = 32.sp,
            lineHeight = 40.sp
        ),
        headlineMedium = TextStyle(
            fontWeight = FontWeight.Normal,
            fontFamily = poppins,
            fontSize = 28.sp,
            lineHeight = 36.sp
        ),
        headlineSmall = TextStyle(
            fontWeight = FontWeight.Normal,
            fontFamily = poppins,
            fontSize = 24.sp,
            lineHeight = 32.sp
        ),

        titleLarge = TextStyle(
            fontWeight = FontWeight.W700,
            fontFamily = poppins,
            fontSize = 22.sp,
            lineHeight = 28.sp
        ),
        titleMedium = TextStyle(
            fontWeight = FontWeight.W700,
            fontFamily = poppins,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.15.sp
        ),
        titleSmall = TextStyle(
            fontWeight = FontWeight.W700,
            fontFamily = poppins,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            letterSpacing = 0.1.sp
        ),

        bodyLarge = TextStyle(
            fontWeight = FontWeight.Normal,
            fontFamily = poppins,
            fontSize = 16.sp,
            lineHeight = 24.sp
        ),
        bodyMedium = TextStyle(
            fontWeight = FontWeight.Normal,
            fontFamily = poppins,
            fontSize = 14.sp,
            lineHeight = 20.sp
        ),
        bodySmall = TextStyle(
            fontWeight = FontWeight.Normal,
            fontFamily = poppins,
            fontSize = 12.sp,
            lineHeight = 16.sp
        ),

        labelLarge = TextStyle(
            fontWeight = FontWeight.W700,
            fontFamily = poppins,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            letterSpacing = 0.1.sp
        ),
        labelMedium = TextStyle(
            fontWeight = FontWeight.W700,
            fontFamily = poppins,
            fontSize = 12.sp,
            lineHeight = 16.sp,
            letterSpacing = 0.5.sp
        ),
        labelSmall = TextStyle(
            fontWeight = FontWeight.W700,
            fontFamily = poppins,
            fontSize = 11.sp,
            lineHeight = 16.sp
        ),
    )
}