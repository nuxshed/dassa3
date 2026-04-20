package com.dass.ims.ui.studentdetails

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.dass.ims.data.UserRole
import com.dass.ims.data.LocalRole
import com.dass.ims.navigation.Routes
import com.dass.ims.ui.ViewModelFactory
import com.dass.ims.ui.shared.ActionButton
import com.dass.ims.ui.shared.Avatar
import com.dass.ims.ui.shared.BottomNavBar
import com.dass.ims.ui.shared.NavTab
import com.dass.ims.ui.shared.PropertyRow
import com.dass.ims.ui.shared.SectionHeader
import com.dass.ims.ui.shared.TopBar
import com.dass.ims.ui.theme.ImsIcon
import com.dass.ims.ui.theme.ImsIcons
import com.dass.ims.ui.theme.ImsTheme
import com.dass.ims.ui.theme.InterFamily
import com.dass.ims.ui.theme.JetBrainsMonoFamily
import com.dass.ims.ui.theme.avatarcolors
import com.dass.ims.ui.theme.subjectcolors

private fun avatarcolor(userid: Long) = avatarcolors[(userid % avatarcolors.size).toInt()]

private fun initials(name: String) = name.split(" ")
    .mapNotNull { it.firstOrNull()?.uppercaseChar() }
    .take(2)
    .joinToString("")

@Composable
fun StudentProfileScreen(navController: NavController, studentid: Long) {
    val vm: StudentProfileViewModel = viewModel(factory = ViewModelFactory(LocalContext.current))
    val state by vm.state.collectAsState()
    val colors = ImsTheme.colors
    val role = LocalRole.current

    LaunchedEffect(studentid) { vm.load(studentid) }

    var expanded by remember { mutableStateOf(setOf("Contact", "Academics")) }

    fun toggle(section: String) {
        expanded = if (section in expanded) expanded - section else expanded + section
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.bg),
    ) {
        TopBar(
            leftslot = {
                ImsIcon(
                    icon = ImsIcons.chevleft,
                    size = 22.dp,
                    tint = colors.text,
                    modifier = Modifier.clickable { navController.popBackStack() },
                )
            },
            rightslot = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(14.dp),
                ) {
                    ImsIcon(icon = ImsIcons.msg, size = 20.dp, tint = colors.textsecondary)
                    ImsIcon(icon = ImsIcons.more, size = 20.dp, tint = colors.textsecondary)
                }
            },
        )

        if (state.loading) {
            Box(
                modifier = Modifier.weight(1f).fillMaxWidth(),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator(color = colors.text, strokeWidth = 2.dp)
            }
        } else {
            val student = state.student

            if (student == null) {
                Box(
                    modifier = Modifier.weight(1f).fillMaxWidth(),
                    contentAlignment = Alignment.Center,
                ) {
                    Text("Student not found", color = colors.textfaint, fontFamily = InterFamily, fontSize = 13.sp)
                }
            } else {
                val ac = avatarcolor(student.userid)

                // header
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, top = 8.dp, bottom = 16.dp),
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.spacedBy(14.dp),
                ) {
                    Avatar(
                        initials = initials(student.name),
                        size = 64.dp,
                        bg = ac.bg,
                        fg = ac.fg,
                    )
                    Column {
                        Text(
                            text = student.name,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = InterFamily,
                            color = colors.text,
                            letterSpacing = (-0.4).sp,
                        )
                        Text(
                            text = "${student.roll} · ${student.batchname} · ${student.category.ifEmpty { "—" }}",
                            fontSize = 12.sp,
                            fontFamily = JetBrainsMonoFamily,
                            color = colors.textsecondary,
                            modifier = Modifier.padding(top = 2.dp),
                        )
                        // active badge
                        Row(
                            modifier = Modifier
                                .padding(top = 6.dp)
                                .background(colors.activebadgebg, RoundedCornerShape(10.dp))
                                .padding(horizontal = 8.dp, vertical = 3.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(5.dp),
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(5.dp)
                                    .background(colors.activebadgefg, CircleShape),
                            )
                            Text(
                                text = "Active",
                                fontSize = 10.sp,
                                fontFamily = InterFamily,
                                fontWeight = FontWeight.Medium,
                                color = colors.activebadgefg,
                            )
                        }
                    }
                }

                // action buttons (admin only)
                if (role.role == UserRole.ADMIN) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, bottom = 10.dp),
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                    ) {
                        ActionButton(
                            label = "Message",
                            icon = ImsIcons.msg,
                            onclick = {},
                            modifier = Modifier.weight(1f),
                        )
                        ActionButton(
                            label = "Edit",
                            icon = ImsIcons.pencil,
                            onclick = { navController.navigate(Routes.stub("student-edit")) },
                            modifier = Modifier.weight(1f),
                        )
                        ActionButton(
                            label = "Transfer",
                            icon = ImsIcons.enter,
                            onclick = {},
                            modifier = Modifier.weight(1f),
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState()),
                ) {
                    // Contact section
                    SectionHeader(
                        label = "Contact",
                        open = "Contact" in expanded,
                        ontoggle = { toggle("Contact") },
                    )
                    if ("Contact" in expanded) {
                        val guardiantext = state.guardians.firstOrNull()
                            ?.let { "${it.name} (${it.relation})" } ?: "—"
                        PropertyRow(label = "Email", value = student.email)
                        PropertyRow(label = "Phone", value = student.phone.ifEmpty { "—" })
                        PropertyRow(label = "Date of birth", value = student.dob.ifEmpty { "—" })
                        PropertyRow(label = "Guardian", value = guardiantext)
                        PropertyRow(label = "Enrolled", value = student.enrollmentdate.ifEmpty { "—" }, showdivider = false)
                    }

                    // Academics section
                    SectionHeader(
                        label = "Academics",
                        open = "Academics" in expanded,
                        ontoggle = { toggle("Academics") },
                    )
                    if ("Academics" in expanded) {
                        state.enrollments.forEachIndexed { i, e ->
                            val islast = i == state.enrollments.lastIndex
                            val sc = subjectcolors[e.subjectcode]
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 20.dp, vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                            ) {
                                // subject code pill
                                Box(
                                    modifier = Modifier
                                        .background(
                                            sc?.bg ?: colors.chipbg,
                                            RoundedCornerShape(4.dp),
                                        )
                                        .padding(horizontal = 3.dp, vertical = 2.dp),
                                ) {
                                    Text(
                                        text = e.subjectcode,
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = JetBrainsMonoFamily,
                                        color = sc?.fg ?: colors.textsecondary,
                                    )
                                }
                                Text(
                                    text = e.subjectname,
                                    fontSize = 13.sp,
                                    fontFamily = InterFamily,
                                    color = colors.text,
                                    modifier = Modifier.weight(1f),
                                )
                                Text(
                                    text = if (e.grade != null) "${e.grade}%" else "—",
                                    fontSize = 12.sp,
                                    fontFamily = JetBrainsMonoFamily,
                                    color = colors.textsecondary,
                                )
                            }
                            if (!islast) {
                                Divider(color = colors.divider, thickness = 1.dp)
                            }
                        }
                    }

                    // Attendance section (collapsed stub)
                    SectionHeader(
                        label = "Attendance",
                        open = "Attendance" in expanded,
                        ontoggle = { toggle("Attendance") },
                    )

                    // Fees section (collapsed stub)
                    SectionHeader(
                        label = "Fees",
                        open = "Fees" in expanded,
                        ontoggle = { toggle("Fees") },
                    )
                }
            }
        }

        BottomNavBar(
            active = NavTab.PEOPLE,
            ontabclick = { tab ->
                when (tab) {
                    NavTab.HOME -> navController.navigate(Routes.DASHBOARD)
                    NavTab.SEARCH -> navController.navigate(Routes.SEARCH)
                    NavTab.SCHEDULE -> navController.navigate(Routes.TIMETABLE)
                    NavTab.PEOPLE -> navController.navigate(Routes.STUDENTS)
                    NavTab.ME -> navController.navigate(Routes.stub("profile"))
                }
            },
        )
    }
}
