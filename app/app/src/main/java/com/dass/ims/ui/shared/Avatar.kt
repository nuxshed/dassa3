package com.dass.ims.ui.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dass.ims.ui.theme.InterFamily

@Composable
fun Avatar(
    initials: String,
    modifier: Modifier = Modifier,
    size: Dp = 32.dp,
    bg: Color = Color(0xFFE9E5DA),
    fg: Color = Color(0xFF6B5C35),
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(bg),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = initials,
            color = fg,
            fontSize = (size.value * 0.38f).sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = InterFamily,
        )
    }
}
