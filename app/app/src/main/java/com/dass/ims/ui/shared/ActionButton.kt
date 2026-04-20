package com.dass.ims.ui.shared

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dass.ims.ui.theme.ImsIcon
import com.dass.ims.ui.theme.ImsTheme
import com.dass.ims.ui.theme.InterFamily

@Composable
fun ActionButton(
    label: String,
    icon: ImageVector,
    onclick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = ImsTheme.colors
    Row(
        modifier = modifier
            .border(1.dp, colors.border, RoundedCornerShape(6.dp))
            .clickable(onClick = onclick)
            .padding(horizontal = 10.dp, vertical = 7.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        ImsIcon(icon = icon, tint = colors.text, size = 13.dp)
        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = colors.text,
            fontFamily = InterFamily,
        )
    }
}
