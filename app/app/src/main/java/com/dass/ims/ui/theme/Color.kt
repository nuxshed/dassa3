package com.dass.ims.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class ImsColors(
    val bg: Color = Color(0xFFFFFFFF),
    val surface: Color = Color(0xFFF7F7F5),
    val border: Color = Color(0xFFEDEDEC),
    val borderstrong: Color = Color(0xFFE3E2E0),
    val text: Color = Color(0xFF37352F),
    val textsecondary: Color = Color(0xFF787774),
    val textfaint: Color = Color(0xFF9B9A97),
    val hover: Color = Color(0xFFF1F1EF),
    val selected: Color = Color(0xFFE8E8E6),
    val accent: Color = Color(0xFF2383E2),
    val red: Color = Color(0xFFE03E3E),
    val green: Color = Color(0xFF0F7B6C),
    val yellow: Color = Color(0xFFDFAB01),
    val chipbg: Color = Color(0xFFF1F1EF),
    val divider: Color = Color(0xFFF1F1EF),

    val alertbg: Color = Color(0xFFFDECE5),
    val alertborder: Color = Color(0xFFF5C9B6),
    val alertfg: Color = Color(0xFF8B3A2C),

    val infobg: Color = Color(0xFFFFF9E6),
    val infoborder: Color = Color(0xFFF3DFA2),
    val infofg: Color = Color(0xFF7C5A00),

    val activebadgebg: Color = Color(0xFFE0F0E5),
    val activebadgefg: Color = Color(0xFF1E6B3B),
)

data class SubjectColor(val bg: Color, val fg: Color)

val subjectcolors = mapOf(
    "OS" to SubjectColor(Color(0xFFFDECC8), Color(0xFF7C4A03)),
    "ALG" to SubjectColor(Color(0xFFDBEDDB), Color(0xFF1E6B3B)),
    "HCI" to SubjectColor(Color(0xFFE8DEEE), Color(0xFF5D3A80)),
    "DBMS" to SubjectColor(Color(0xFFD3E5EF), Color(0xFF1D4F6E)),
    "CN" to SubjectColor(Color(0xFFFFE2DD), Color(0xFF8B3A2C)),
    "SE" to SubjectColor(Color(0xFFEEE0DA), Color(0xFF6B4A3E)),
    "MATH" to SubjectColor(Color(0xFFE3E2E0), Color(0xFF3C3A36)),
)

data class AvatarColor(val bg: Color, val fg: Color)

val avatarcolors = listOf(
    AvatarColor(Color(0xFFE9E5DA), Color(0xFF6B5C35)),
    AvatarColor(Color(0xFFE8DEEE), Color(0xFF5D3A80)),
    AvatarColor(Color(0xFFFDECC8), Color(0xFF7C4A03)),
    AvatarColor(Color(0xFFDBEDDB), Color(0xFF1E6B3B)),
    AvatarColor(Color(0xFFD3E5EF), Color(0xFF1D4F6E)),
    AvatarColor(Color(0xFFFFE2DD), Color(0xFF8B3A2C)),
    AvatarColor(Color(0xFFEEE0DA), Color(0xFF6B4A3E)),
    AvatarColor(Color(0xFFE6DFD1), Color(0xFF6B5C35)),
)

val LocalImsColors = staticCompositionLocalOf { ImsColors() }
