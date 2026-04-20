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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dass.ims.ui.theme.ImsIcon
import com.dass.ims.ui.theme.ImsIcons
import com.dass.ims.ui.theme.ImsTheme
import com.dass.ims.ui.theme.InterFamily

enum class NavTab(val label: String, val icon: ImageVector) {
    HOME("Home", ImsIcons.navhome),
    SEARCH("Search", ImsIcons.navsearch),
    SCHEDULE("Schedule", ImsIcons.navschedule),
    PEOPLE("People", ImsIcons.navpeople),
    ME("Me", ImsIcons.navme),
}

@Composable
fun BottomNavBar(
    active: NavTab,
    ontabclick: (NavTab) -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = ImsTheme.colors
    Column(modifier = modifier.fillMaxWidth()) {
        Divider(color = colors.border, thickness = 1.dp)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .padding(top = 8.dp, bottom = 24.dp),
        ) {
            NavTab.entries.forEach { tab ->
                val on = tab == active
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { ontabclick(tab) }
                        .padding(vertical = 6.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(3.dp),
                ) {
                    ImsIcon(
                        icon = tab.icon,
                        tint = if (on) colors.text else colors.textfaint,
                        size = 22.dp,
                    )
                    Text(
                        text = tab.label,
                        fontSize = 10.sp,
                        fontWeight = if (on) FontWeight.SemiBold else FontWeight.Normal,
                        color = if (on) colors.text else colors.textfaint,
                        fontFamily = InterFamily,
                    )
                }
            }
        }
    }
}
