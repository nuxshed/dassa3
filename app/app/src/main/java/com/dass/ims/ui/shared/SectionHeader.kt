package com.dass.ims.ui.shared

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dass.ims.ui.theme.ImsIcon
import com.dass.ims.ui.theme.ImsIcons
import com.dass.ims.ui.theme.ImsTheme
import com.dass.ims.ui.theme.InterFamily

@Composable
fun SectionHeader(
    label: String,
    modifier: Modifier = Modifier,
    open: Boolean = true,
    ontoggle: (() -> Unit)? = null,
    right: @Composable (() -> Unit)? = null,
) {
    val colors = ImsTheme.colors
    Row(
        modifier = modifier
            .fillMaxWidth()
            .then(if (ontoggle != null) Modifier.clickable { ontoggle() } else Modifier)
            .padding(start = 20.dp, end = 20.dp, top = 14.dp, bottom = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            ImsIcon(
                icon = if (open) ImsIcons.chevdown else ImsIcons.chevright,
                tint = colors.textsecondary,
                size = 12.dp,
            )
            Text(
                text = label.uppercase(),
                color = colors.textsecondary,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 0.8.sp,
                fontFamily = InterFamily,
            )
        }
        right?.invoke()
    }
}
