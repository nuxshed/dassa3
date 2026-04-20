package com.dass.ims.ui.login

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dass.ims.data.LocalRole
import com.dass.ims.data.UserRole
import com.dass.ims.ui.theme.*

@Composable
fun LoginScreen(onlogin: () -> Unit) {
    val colors = ImsTheme.colors
    val role = LocalRole.current

    Scaffold(containerColor = colors.bg) { pad ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(pad)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .background(Color(0xFF111111), RoundedCornerShape(10.dp)),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    "i",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = JetBrainsMonoFamily,
                )
            }
            Spacer(Modifier.height(16.dp))
            Text(
                "IMS",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = (-0.5).sp,
                color = colors.text,
            )
            Text(
                "Institute Management System",
                fontSize = 13.sp,
                color = colors.textsecondary,
                modifier = Modifier.padding(top = 4.dp),
            )

            Spacer(Modifier.height(40.dp))

            RoleCard(
                title = "Administrator",
                subtitle = "Full access · manage everything",
                icon = ImsIcons.usercog,
                onclick = {
                    role.role = UserRole.ADMIN
                    role.currentuserid = 1L
                    onlogin()
                },
                colors = colors,
            )

            Spacer(Modifier.height(10.dp))

            RoleCard(
                title = "Student",
                subtitle = "View schedule, grades & profile",
                icon = ImsIcons.user,
                onclick = {
                    role.role = UserRole.STUDENT
                    role.currentuserid = 2L
                    onlogin()
                },
                colors = colors,
            )

            Spacer(Modifier.height(24.dp))
            Text(
                "Demo mode · no real authentication",
                fontSize = 11.sp,
                color = colors.textfaint,
            )
        }
    }
}

@Composable
private fun RoleCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    onclick: () -> Unit,
    colors: ImsColors,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, colors.border, RoundedCornerShape(10.dp))
            .background(colors.surface, RoundedCornerShape(10.dp))
            .clickable(onClick = onclick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .background(colors.chipbg, RoundedCornerShape(10.dp)),
            contentAlignment = Alignment.Center,
        ) {
            ImsIcon(icon = icon, tint = colors.text, size = 22.dp)
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(
                title,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = colors.text,
            )
            Text(
                subtitle,
                fontSize = 12.sp,
                color = colors.textsecondary,
                modifier = Modifier.padding(top = 1.dp),
            )
        }
        ImsIcon(icon = ImsIcons.chevright, tint = colors.accent, size = 18.dp)
    }
}
