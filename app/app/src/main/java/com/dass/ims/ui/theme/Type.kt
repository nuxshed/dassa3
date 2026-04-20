package com.dass.ims.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.dass.ims.R

val InterFamily = FontFamily(
    Font(R.font.inter_variable, FontWeight.Normal),
    Font(R.font.inter_variable, FontWeight.Medium),
    Font(R.font.inter_variable, FontWeight.SemiBold),
    Font(R.font.inter_variable, FontWeight.Bold),
)

val JetBrainsMonoFamily = FontFamily(
    Font(R.font.jetbrains_mono_variable, FontWeight.Normal),
    Font(R.font.jetbrains_mono_variable, FontWeight.Medium),
)

val Display = TextStyle(
    fontFamily = InterFamily,
    fontWeight = FontWeight.Bold,
    fontSize = 24.sp,
    letterSpacing = (-0.5).sp,
)

val Heading = TextStyle(
    fontFamily = InterFamily,
    fontWeight = FontWeight.SemiBold,
    fontSize = 18.sp,
    letterSpacing = (-0.3).sp,
)

val Body = TextStyle(
    fontFamily = InterFamily,
    fontWeight = FontWeight.Normal,
    fontSize = 15.sp,
)

val Label = TextStyle(
    fontFamily = InterFamily,
    fontWeight = FontWeight.SemiBold,
    fontSize = 11.sp,
    letterSpacing = 0.8.sp,
)

val Caption = TextStyle(
    fontFamily = InterFamily,
    fontWeight = FontWeight.Normal,
    fontSize = 12.sp,
)

val Mono = TextStyle(
    fontFamily = JetBrainsMonoFamily,
    fontWeight = FontWeight.Normal,
    fontSize = 13.sp,
)

val ImsTypography = Typography(
    displayLarge = Display,
    headlineMedium = Heading,
    bodyLarge = Body,
    bodyMedium = Body.copy(fontSize = 14.sp),
    bodySmall = Body.copy(fontSize = 13.sp),
    labelLarge = Label.copy(fontWeight = FontWeight.Medium, fontSize = 12.sp),
    labelMedium = Label,
    labelSmall = Caption,
    titleLarge = TextStyle(
        fontFamily = InterFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        letterSpacing = (-0.3).sp,
    ),
    titleMedium = TextStyle(
        fontFamily = InterFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 15.sp,
        letterSpacing = (-0.2).sp,
    ),
    titleSmall = TextStyle(
        fontFamily = InterFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
    ),
)
