package com.dass.ims.ui.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.dass.ims.data.LocalRole
import com.dass.ims.data.UserRole
import com.dass.ims.navigation.Routes
import com.dass.ims.ui.ViewModelFactory
import com.dass.ims.ui.shared.Avatar
import com.dass.ims.ui.shared.BottomNavBar
import com.dass.ims.ui.shared.ModuleCard
import com.dass.ims.ui.shared.NavTab
import com.dass.ims.ui.shared.SearchField
import com.dass.ims.ui.shared.SectionHeader
import com.dass.ims.ui.theme.ImsIcon
import com.dass.ims.ui.theme.ImsIcons
import com.dass.ims.ui.theme.ImsTheme
import com.dass.ims.ui.theme.InterFamily
import com.dass.ims.ui.theme.JetBrainsMonoFamily
import com.dass.ims.ui.theme.avatarcolors

private data class Module(
    val name: String,
    val description: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val implemented: Boolean,
    val route: String,
)

@Composable
fun DashboardScreen(navController: NavController) {
    val vm: DashboardViewModel = viewModel(factory = ViewModelFactory(LocalContext.current))
    val role = LocalRole.current
    val isadmin = role.role == UserRole.ADMIN
    val userid = role.currentuserid
    val state by vm.state.collectAsState()
    val colors = ImsTheme.colors

    LaunchedEffect(userid) { vm.load(userid, isadmin) }

    if (isadmin) {
        AdminDashboard(navController, state, userid)
    } else {
        StudentDashboard(navController, state, userid)
    }
}

@Composable
private fun AdminDashboard(
    navController: NavController,
    state: DashboardState,
    userid: Long,
) {
    val colors = ImsTheme.colors
    val modules = adminmodules(navController)
    val firstname = state.user?.name?.split(" ")?.firstOrNull() ?: "Admin"
    val initials = avatarinitials(state.user?.name ?: "A")
    val avatarcolor = avatarcolors[(userid % avatarcolors.size).toInt()]

    Column(modifier = Modifier.fillMaxSize().background(colors.bg).statusBarsPadding()) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .background(colors.text, RoundedCornerShape(6.dp)),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = "i",
                        color = colors.bg,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = InterFamily,
                    )
                }
                Text(
                    text = "IMS",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = colors.text,
                    fontFamily = InterFamily,
                )
            }
            Avatar(
                initials = initials,
                size = 32.dp,
                bg = avatarcolor.bg,
                fg = avatarcolor.fg,
            )
        }

        // Search
        SearchField(
            value = "",
            onchange = {},
            placeholder = "Search students, slots, modules…",
            onclick = { navController.navigate(Routes.SEARCH) },
        )

        // Greeting
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(top = 6.dp, bottom = 10.dp),
        ) {
            Text(
                text = "Good morning, $firstname",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = colors.text,
                fontFamily = InterFamily,
                letterSpacing = (-0.5).sp,
            )
            Spacer(Modifier.height(2.dp))
            Text(
                text = "2 pending approvals · 1 schedule conflict",
                fontSize = 13.sp,
                color = colors.textsecondary,
                fontFamily = InterFamily,
            )
        }

        SectionHeader("Modules")

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(bottom = 16.dp),
        ) {
            items(modules) { mod ->
                ModuleCard(
                    name = mod.name,
                    description = mod.description,
                    icon = mod.icon,
                    implemented = mod.implemented,
                    onclick = { navController.navigate(mod.route) },
                )
            }
        }

        BottomNavBar(
            active = NavTab.HOME,
            ontabclick = { tab ->
                when (tab) {
                    NavTab.HOME -> {}
                    NavTab.SEARCH -> navController.navigate(Routes.SEARCH)
                    NavTab.SCHEDULE -> navController.navigate(Routes.TIMETABLE)
                    NavTab.PEOPLE -> navController.navigate(Routes.STUDENTS)
                    NavTab.ME -> navController.navigate(Routes.stub("profile"))
                }
            },
        )
    }
}

@Composable
private fun StudentDashboard(
    navController: NavController,
    state: DashboardState,
    userid: Long,
) {
    val colors = ImsTheme.colors
    val firstname = state.user?.name?.split(" ")?.firstOrNull() ?: "Student"
    val batchname = state.student?.batchname ?: ""
    val studentid = state.student?.studentid ?: 0L

    LazyColumn(
        modifier = Modifier.fillMaxSize().background(colors.bg).statusBarsPadding(),
    ) {
        // Header
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = "My Home",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = colors.text,
                    fontFamily = InterFamily,
                )
                Text(
                    text = "student · $batchname",
                    fontSize = 11.sp,
                    color = colors.textsecondary,
                    fontFamily = JetBrainsMonoFamily,
                )
            }
        }

        // Greeting
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .padding(top = 14.dp, bottom = 8.dp),
            ) {
                Text(
                    text = "Hi, $firstname 👋",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = colors.text,
                    fontFamily = InterFamily,
                    letterSpacing = (-0.6).sp,
                )
                Spacer(Modifier.height(2.dp))
                Text(
                    text = "3 classes today",
                    fontSize = 13.sp,
                    color = colors.textsecondary,
                    fontFamily = InterFamily,
                )
            }
        }

        // Search
        item {
            SearchField(
                value = "",
                onchange = {},
                placeholder = "Search classes, people, notes…",
                onclick = { navController.navigate(Routes.SEARCH) },
            )
        }

        // Next up section
        item { SectionHeader("Next up") }

        // Next class card
        item {
            val nextup = state.enrollments.firstOrNull()
            if (nextup != null) {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .fillMaxWidth()
                        .border(1.dp, colors.borderstrong, RoundedCornerShape(10.dp))
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "8:30 – 9:55",
                            fontSize = 12.sp,
                            color = colors.textsecondary,
                            fontFamily = JetBrainsMonoFamily,
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            text = nextup.subjectname,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = colors.text,
                            fontFamily = InterFamily,
                        )
                        Spacer(Modifier.height(2.dp))
                        Text(
                            text = "Lecture",
                            fontSize = 12.sp,
                            color = colors.textsecondary,
                            fontFamily = InterFamily,
                        )
                    }
                    Box(
                        modifier = Modifier
                            .background(colors.chipbg, RoundedCornerShape(6.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                    ) {
                        Text(
                            text = "Room 101",
                            fontSize = 11.sp,
                            color = colors.textsecondary,
                            fontFamily = InterFamily,
                        )
                    }
                }
            } else {
                Text(
                    text = "No upcoming classes",
                    fontSize = 13.sp,
                    color = colors.textfaint,
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp),
                    fontFamily = InterFamily,
                )
            }
        }

        // My classes section
        item { SectionHeader("My classes") }

        items(state.enrollments) { enrollment ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp)
                    .padding(top = 10.dp, bottom = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier
                        .size(26.dp)
                        .background(colors.surface, RoundedCornerShape(6.dp)),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = enrollment.subjectcode.take(4),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = colors.textsecondary,
                        fontFamily = JetBrainsMonoFamily,
                    )
                }
                Spacer(Modifier.width(10.dp))
                val label = if (enrollment.iselective) "${enrollment.subjectname} (elective)" else enrollment.subjectname
                Text(
                    text = label,
                    fontSize = 14.sp,
                    color = colors.text,
                    fontFamily = InterFamily,
                    modifier = Modifier.weight(1f),
                )
                val grade = enrollment.grade
                Text(
                    text = if (grade != null) "$grade%" else "—",
                    fontSize = 12.sp,
                    color = colors.textsecondary,
                    fontFamily = JetBrainsMonoFamily,
                )
            }
            Divider(color = colors.divider, thickness = 1.dp, modifier = Modifier.padding(horizontal = 4.dp))
        }

        // Latest news section
        item { SectionHeader("Latest news") }

        items(state.news) { news ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .padding(vertical = 8.dp),
            ) {
                Text(
                    text = news.title,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = colors.text,
                    fontFamily = InterFamily,
                )
                Spacer(Modifier.height(2.dp))
                Text(
                    text = "${news.category} · ${relativetime(news.timestamp)}",
                    fontSize = 11.sp,
                    color = colors.textfaint,
                    fontFamily = InterFamily,
                )
            }
            Divider(color = colors.divider, thickness = 1.dp, modifier = Modifier.padding(horizontal = 20.dp))
        }

        // Bottom nav
        item {
            BottomNavBar(
                active = NavTab.HOME,
                ontabclick = { tab ->
                    when (tab) {
                        NavTab.HOME -> {}
                        NavTab.SEARCH -> navController.navigate(Routes.SEARCH)
                        NavTab.SCHEDULE -> navController.navigate(Routes.TIMETABLE)
                        NavTab.PEOPLE -> if (studentid > 0) navController.navigate(Routes.studentprofile(studentid))
                        NavTab.ME -> navController.navigate(Routes.studentprofile(studentid))
                    }
                },
            )
        }
    }
}

private fun avatarinitials(name: String): String {
    val words = name.trim().split(" ").filter { it.isNotEmpty() }
    return words.take(2).joinToString("") { it.first().uppercaseChar().toString() }
}

private fun relativetime(ts: Long): String {
    val now = System.currentTimeMillis()
    val diff = now - ts
    val mins = diff / 60_000
    val hours = diff / 3_600_000
    val days = diff / 86_400_000
    return when {
        mins < 60 -> "${mins}m ago"
        hours < 24 -> "${hours}h ago"
        days == 1L -> "yesterday"
        else -> "${days}d ago"
    }
}

@Composable
private fun adminmodules(navController: NavController): List<Module> = listOf(
    Module("Timetable", "weekly schedule", ImsIcons.cal, true, Routes.TIMETABLE),
    Module("Student Details", "directory & profiles", ImsIcons.users, true, Routes.STUDENTS),
    Module("Attendance", "coming soon", ImsIcons.clip, false, Routes.stub("attendance")),
    Module("Examinations", "coming soon", ImsIcons.file, false, Routes.stub("examinations")),
    Module("Finance", "coming soon", ImsIcons.bank, false, Routes.stub("finance")),
    Module("Messages", "coming soon", ImsIcons.msg, false, Routes.stub("messages")),
    Module("News", "coming soon", ImsIcons.paper, false, Routes.stub("news")),
    Module("Human Resources", "coming soon", ImsIcons.brief, false, Routes.stub("hr")),
    Module("Manage Users", "coming soon", ImsIcons.usercog, false, Routes.stub("users")),
    Module("Admission", "coming soon", ImsIcons.userplus, false, Routes.stub("admission")),
)
