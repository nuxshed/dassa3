package com.dass.ims.ui.shared

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dass.ims.ui.theme.ImsTheme
import androidx.compose.foundation.layout.Column

@Composable
fun PropertyRow(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
    showdivider: Boolean = true,
) {
    val colors = ImsTheme.colors
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 7.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = label,
                color = colors.textsecondary,
                fontSize = 12.sp,
                modifier = Modifier.weight(0.4f),
            )
            Text(
                text = value,
                color = colors.text,
                fontSize = 13.sp,
                modifier = Modifier.weight(0.6f),
            )
        }
        if (showdivider) {
            Divider(color = colors.divider, thickness = 1.dp)
        }
    }
}
