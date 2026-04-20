package com.dass.ims.ui.stub

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.dass.ims.ui.shared.BottomNavBar
import com.dass.ims.ui.shared.NavTab
import com.dass.ims.ui.shared.TopBar
import com.dass.ims.ui.theme.ImsIcon
import com.dass.ims.ui.theme.ImsIcons
import com.dass.ims.ui.theme.ImsTheme

// Map module name to icon — extend as needed
private fun iconformodule(module: String) = when (module.lowercase()) {
    "attendance" -> ImsIcons.clip
    "examinations" -> ImsIcons.file
    "finance" -> ImsIcons.bank
    "messages" -> ImsIcons.msg
    "news" -> ImsIcons.paper
    "human resources" -> ImsIcons.brief
    "manage users" -> ImsIcons.usercog
    "admission" -> ImsIcons.userplus
    else -> ImsIcons.file
}

@Composable
fun StubScreen(navController: NavController, module: String) {
    val colors = ImsTheme.colors
    Scaffold(containerColor = colors.bg) { pad ->
        Column(
            modifier = Modifier.fillMaxSize().padding(pad),
        ) {
            TopBar(
                leftslot = {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        ImsIcon(
                            icon = ImsIcons.chevleft,
                            tint = colors.textsecondary,
                            size = 22.dp,
                            modifier = Modifier.clickable { navController.popBackStack() }
                        )
                        Text(module, fontSize = 15.sp, fontWeight = FontWeight.SemiBold, color = colors.text)
                    }
                }
            )
            Box(
                modifier = Modifier.weight(1f).fillMaxWidth(),
                contentAlignment = Alignment.Center,
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(40.dp),
                ) {
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .background(colors.surface, RoundedCornerShape(16.dp))
                            .border(1.dp, colors.border, RoundedCornerShape(16.dp)),
                        contentAlignment = Alignment.Center,
                    ) {
                        ImsIcon(icon = iconformodule(module), tint = colors.textsecondary, size = 32.dp)
                    }
                    Spacer(Modifier.height(20.dp))
                    Text(module, fontSize = 18.sp, fontWeight = FontWeight.SemiBold, color = colors.text)
                    Spacer(Modifier.height(6.dp))
                    Text(
                        "This module is not available yet. Check back in a future release.",
                        fontSize = 13.sp,
                        color = colors.textsecondary,
                        textAlign = TextAlign.Center,
                        lineHeight = 20.sp,
                        modifier = Modifier.widthIn(max = 240.dp),
                    )
                }
            }
            BottomNavBar(active = NavTab.HOME, ontabclick = { tab ->
                when (tab) {
                    NavTab.HOME -> navController.navigate("dashboard") { launchSingleTop = true }
                    NavTab.SEARCH -> navController.navigate("search")
                    NavTab.SCHEDULE -> navController.navigate("timetable")
                    NavTab.PEOPLE -> navController.navigate("students")
                    NavTab.ME -> { /* navigate to self profile */ }
                }
            })
        }
    }
}
