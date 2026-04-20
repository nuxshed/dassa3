package com.dass.ims.ui.shared

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dass.ims.ui.theme.ImsTheme
import com.dass.ims.ui.theme.InterFamily
import com.dass.ims.ui.theme.JetBrainsMonoFamily
import androidx.compose.foundation.layout.Column

@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    title: String? = null,
    mono: Boolean = false,
    border: Boolean = true,
    leftslot: @Composable (() -> Unit)? = null,
    rightslot: @Composable (() -> Unit)? = null,
) {
    val colors = ImsTheme.colors
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            leftslot?.invoke() ?: title?.let {
                Text(
                    text = it,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = if (mono) JetBrainsMonoFamily else InterFamily,
                    color = colors.text,
                )
            }
            rightslot?.invoke()
        }
        if (border) {
            Divider(color = colors.border, thickness = 1.dp)
        }
    }
}
