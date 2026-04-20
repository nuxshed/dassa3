package com.dass.ims.ui.shared

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dass.ims.ui.theme.ImsTheme
import com.dass.ims.ui.theme.JetBrainsMonoFamily

@Composable
fun StudentRow(
    name: String,
    roll: String,
    batch: String,
    gpa: String,
    programme: String,
    initials: String,
    avatarbg: Color,
    avatarfg: Color,
    onclick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = ImsTheme.colors
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onclick)
                .padding(horizontal = 20.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Avatar(initials = initials, size = 36.dp, bg = avatarbg, fg = avatarfg)
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = name,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = colors.text,
                )
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = roll,
                        fontSize = 11.sp,
                        color = colors.textsecondary,
                        fontFamily = JetBrainsMonoFamily,
                    )
                    Text(
                        text = "·",
                        fontSize = 11.sp,
                        color = colors.textsecondary,
                    )
                    Text(
                        text = batch,
                        fontSize = 11.sp,
                        color = colors.textsecondary,
                    )
                }
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = gpa,
                    fontSize = 12.sp,
                    fontFamily = JetBrainsMonoFamily,
                    fontWeight = FontWeight.Medium,
                    color = colors.text,
                )
                Text(
                    text = programme,
                    fontSize = 10.sp,
                    color = colors.textfaint,
                )
            }
        }
        Divider(color = colors.divider, thickness = 1.dp)
    }
}
