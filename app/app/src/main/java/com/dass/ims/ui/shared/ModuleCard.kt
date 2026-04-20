package com.dass.ims.ui.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dass.ims.ui.theme.ImsIcon
import com.dass.ims.ui.theme.ImsTheme

@Composable
fun ModuleCard(
    name: String,
    description: String,
    icon: ImageVector,
    implemented: Boolean,
    onclick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = ImsTheme.colors
    Column(
        modifier = modifier
            .defaultMinSize(minHeight = 92.dp)
            .border(1.dp, colors.border, RoundedCornerShape(10.dp))
            .background(
                if (implemented) colors.bg else colors.surface,
                RoundedCornerShape(10.dp),
            )
            .alpha(if (implemented) 1f else 0.82f)
            .clickable(onClick = onclick)
            .padding(horizontal = 12.dp, vertical = 14.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .background(
                    if (implemented) colors.chipbg else colors.surface,
                    RoundedCornerShape(8.dp),
                ),
            contentAlignment = Alignment.Center,
        ) {
            ImsIcon(
                icon = icon,
                tint = if (implemented) colors.text else colors.textfaint,
                size = 18.dp,
            )
        }
        Column {
            Text(
                text = name,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = if (implemented) colors.text else colors.textsecondary,
                lineHeight = (13 * 1.25).sp,
            )
            Text(
                text = description,
                fontSize = 11.sp,
                color = colors.textfaint,
                modifier = Modifier.padding(top = 2.dp),
            )
        }
    }
}
