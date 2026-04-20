package com.dass.ims.ui.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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

@Composable
fun ImsFilterChip(
    label: String,
    modifier: Modifier = Modifier,
    ondismiss: (() -> Unit)? = null,
) {
    val colors = ImsTheme.colors
    Row(
        modifier = modifier
            .background(colors.chipbg, RoundedCornerShape(12.dp))
            .padding(start = 10.dp, end = 8.dp, top = 3.dp, bottom = 3.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        Text(
            text = label,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium,
            color = colors.text,
        )
        ondismiss?.let {
            ImsIcon(
                ImsIcons.x,
                tint = colors.textsecondary,
                size = 10.dp,
                modifier = Modifier.clickable { it() },
            )
        }
    }
}
