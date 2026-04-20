package com.dass.ims.ui.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dass.ims.ui.theme.ImsIcon
import com.dass.ims.ui.theme.ImsIcons
import com.dass.ims.ui.theme.ImsTheme

@Composable
fun SearchField(
    value: String,
    onchange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "Search…",
    compact: Boolean = false,
    onclick: (() -> Unit)? = null,
) {
    val colors = ImsTheme.colors
    val shape = RoundedCornerShape(8.dp)
    val hpad = if (compact) 16.dp else 20.dp
    val vpad = if (compact) 8.dp else 10.dp

    Row(
        modifier = modifier
            .padding(horizontal = hpad, vertical = vpad)
            .fillMaxWidth()
            .height(40.dp)
            .background(colors.surface, shape)
            .border(1.dp, colors.border, shape)
            .then(if (onclick != null) Modifier.clickable { onclick() } else Modifier)
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ImsIcon(ImsIcons.search, tint = colors.textsecondary, size = 16.dp)
        Spacer(Modifier.width(10.dp))

        if (onclick != null) {
            Text(
                text = if (value.isEmpty()) placeholder else value,
                color = if (value.isEmpty()) colors.textfaint else colors.text,
                fontSize = 14.sp,
                modifier = Modifier.weight(1f),
            )
        } else {
            Box(modifier = Modifier.weight(1f)) {
                if (value.isEmpty()) {
                    Text(
                        text = placeholder,
                        color = colors.textfaint,
                        fontSize = 14.sp,
                    )
                }
                BasicTextField(
                    value = value,
                    onValueChange = onchange,
                    singleLine = true,
                    textStyle = androidx.compose.ui.text.TextStyle(
                        color = colors.text,
                        fontSize = 14.sp,
                    ),
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }

        if (value.isNotEmpty()) {
            ImsIcon(
                ImsIcons.x,
                tint = colors.textfaint,
                size = 14.dp,
                modifier = Modifier.clickable { onchange("") },
            )
        }
    }
}
