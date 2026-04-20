package com.dass.ims.ui.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dass.ims.ui.theme.ImsTheme
import com.dass.ims.ui.theme.subjectcolors

@Composable
fun SlotPill(
    code: String,
    modifier: Modifier = Modifier,
    warn: Boolean = false,
) {
    val colors = ImsTheme.colors
    val style = subjectcolors[code]
    val bg = style?.bg ?: colors.chipbg
    val fg = style?.fg ?: colors.text

    Box(
        modifier = modifier
            .then(
                if (warn) Modifier.border(
                    width = 2.dp,
                    color = colors.red,
                    shape = RoundedCornerShape(topStart = 0.dp, bottomStart = 0.dp, topEnd = 4.dp, bottomEnd = 4.dp),
                ) else Modifier
            )
            .background(bg, RoundedCornerShape(4.dp))
            .padding(horizontal = 6.dp, vertical = 3.dp),
    ) {
        Text(
            text = code,
            color = fg,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}
