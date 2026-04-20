package com.dass.ims.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.dass.ims.data.local.StudentWithUser
import com.dass.ims.data.local.entity.BatchEntity
import com.dass.ims.data.local.entity.SubjectEntity
import com.dass.ims.navigation.Routes
import com.dass.ims.ui.ViewModelFactory
import com.dass.ims.ui.shared.Avatar
import com.dass.ims.ui.theme.ImsIcons
import com.dass.ims.ui.theme.ImsTheme
import com.dass.ims.ui.theme.InterFamily
import com.dass.ims.ui.theme.JetBrainsMonoFamily
import com.dass.ims.ui.theme.ImsColors
import com.dass.ims.ui.theme.ImsIcon
import com.dass.ims.ui.theme.avatarcolors

private fun initials(name: String) =
    name.split(" ").mapNotNull { it.firstOrNull()?.uppercaseChar() }.take(2).joinToString("")

@Composable
fun SearchScreen(navController: NavController) {
    val vm: SearchViewModel = viewModel(factory = ViewModelFactory(LocalContext.current))
    val state by vm.state.collectAsState()
    val colors = ImsTheme.colors
    val focusRequester = remember { FocusRequester() }
    val bordercolor = colors.border

    LaunchedEffect(Unit) { focusRequester.requestFocus() }

    Scaffold(containerColor = colors.bg) { pad ->
        Column(modifier = Modifier.fillMaxSize().padding(pad)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .drawBehind {
                        drawLine(
                            color = bordercolor,
                            start = Offset(0f, size.height),
                            end = Offset(size.width, size.height),
                            strokeWidth = 1.dp.toPx(),
                        )
                    }
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                ImsIcon(ImsIcons.search, tint = colors.textsecondary, size = 18.dp)
                BasicTextField(
                    value = state.query,
                    onValueChange = { vm.search(it) },
                    modifier = Modifier.weight(1f).focusRequester(focusRequester),
                    textStyle = TextStyle(
                        fontSize = 16.sp,
                        color = colors.text,
                        fontFamily = InterFamily,
                    ),
                    singleLine = true,
                    decorationBox = { inner ->
                        if (state.query.isEmpty()) {
                            Text(
                                "Search…",
                                fontSize = 16.sp,
                                color = colors.textfaint,
                                fontFamily = InterFamily,
                            )
                        }
                        inner()
                    },
                )
                Box(
                    modifier = Modifier
                        .background(colors.surface, RoundedCornerShape(4.dp))
                        .border(1.dp, colors.border, RoundedCornerShape(4.dp))
                        .clickable { navController.popBackStack() }
                        .padding(horizontal = 7.dp, vertical = 2.dp),
                ) {
                    Text("esc", fontSize = 10.sp, fontFamily = JetBrainsMonoFamily, color = colors.textsecondary)
                }
            }

            LazyColumn(modifier = Modifier.weight(1f)) {
                if (state.batches.isNotEmpty() || state.subjects.isNotEmpty()) {
                    item { SectionLabel("Pages", colors) }
                    items(state.batches) { batch ->
                        PageRow(
                            label = "${batch.name} — Batch page",
                            category = "Batches",
                            onclick = { navController.navigate(Routes.TIMETABLE) },
                            colors = colors,
                        )
                    }
                    items(state.subjects) { sub ->
                        PageRow(
                            label = sub.name,
                            category = "Subject",
                            onclick = { navController.navigate(Routes.stub(sub.name)) },
                            colors = colors,
                        )
                    }
                }

                if (state.students.isNotEmpty()) {
                    item { SectionLabel("People", colors) }
                    items(state.students) { s ->
                        PersonRow(
                            student = s,
                            onclick = { navController.navigate(Routes.studentprofile(s.studentid)) },
                            colors = colors,
                        )
                    }
                }

                if (state.query.isNotEmpty()) {
                    item { SectionLabel("Actions", colors) }
                    item {
                        ActionRow(
                            label = "Browse all students",
                            icon = ImsIcons.users,
                            onclick = { navController.navigate(Routes.STUDENTS) },
                            colors = colors,
                        )
                    }
                    item {
                        ActionRow(
                            label = "View timetable",
                            icon = ImsIcons.cal,
                            onclick = { navController.navigate(Routes.TIMETABLE) },
                            colors = colors,
                        )
                    }
                    item {
                        ActionRow(
                            label = "New admission",
                            icon = ImsIcons.userplus,
                            onclick = { navController.navigate(Routes.stub("Admission")) },
                            colors = colors,
                        )
                    }
                }
            }

            Divider(color = colors.border, thickness = 1.dp)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                HintChip("↵", "open", colors)
                HintChip("esc", "close", colors)
            }
        }
    }
}

@Composable
private fun SectionLabel(label: String, colors: ImsColors) {
    Text(
        text = label.uppercase(),
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 4.dp),
        fontSize = 11.sp,
        color = colors.textsecondary,
        fontFamily = InterFamily,
        fontWeight = FontWeight.SemiBold,
        letterSpacing = 0.8.sp,
    )
}

@Composable
private fun PageRow(
    label: String,
    category: String,
    onclick: () -> Unit,
    colors: ImsColors,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onclick)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Box(
            modifier = Modifier
                .size(28.dp)
                .background(colors.surface, RoundedCornerShape(6.dp))
                .border(1.dp, colors.border, RoundedCornerShape(6.dp)),
            contentAlignment = Alignment.Center,
        ) {
            ImsIcon(ImsIcons.file, tint = colors.textsecondary, size = 14.dp)
        }
        Text(
            text = label,
            modifier = Modifier.weight(1f),
            fontSize = 14.sp,
            color = colors.text,
            fontFamily = InterFamily,
        )
        Text(
            text = category,
            fontSize = 11.sp,
            color = colors.textfaint,
            fontFamily = InterFamily,
        )
    }
}

@Composable
private fun PersonRow(
    student: StudentWithUser,
    onclick: () -> Unit,
    colors: ImsColors,
) {
    val idx = (student.studentid % avatarcolors.size).toInt()
    val acolor = avatarcolors[idx]
    val ini = initials(student.name)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onclick)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Avatar(initials = ini, size = 28.dp, bg = acolor.bg, fg = acolor.fg)
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = student.name,
                fontSize = 14.sp,
                color = colors.text,
                fontFamily = InterFamily,
            )
            Text(
                text = student.roll,
                fontSize = 11.sp,
                color = colors.textfaint,
                fontFamily = JetBrainsMonoFamily,
            )
        }
        Text(
            text = "Student",
            fontSize = 11.sp,
            color = colors.textfaint,
            fontFamily = InterFamily,
        )
    }
}

@Composable
private fun ActionRow(
    label: String,
    icon: ImageVector,
    onclick: () -> Unit,
    colors: ImsColors,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onclick)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Box(
            modifier = Modifier
                .size(28.dp)
                .background(colors.surface, RoundedCornerShape(6.dp))
                .border(1.dp, colors.border, RoundedCornerShape(6.dp)),
            contentAlignment = Alignment.Center,
        ) {
            ImsIcon(icon, tint = colors.textsecondary, size = 14.dp)
        }
        Text(
            text = label,
            modifier = Modifier.weight(1f),
            fontSize = 14.sp,
            color = colors.text,
            fontFamily = InterFamily,
        )
    }
}

@Composable
private fun HintChip(key: String, desc: String, colors: ImsColors) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Box(
            modifier = Modifier
                .background(colors.surface, RoundedCornerShape(3.dp))
                .border(1.dp, colors.border, RoundedCornerShape(3.dp))
                .padding(horizontal = 5.dp, vertical = 1.dp),
        ) {
            Text(
                text = key,
                fontSize = 10.sp,
                fontFamily = JetBrainsMonoFamily,
                color = colors.textsecondary,
            )
        }
        Text(
            text = desc,
            fontSize = 10.sp,
            fontFamily = JetBrainsMonoFamily,
            color = colors.textfaint,
        )
    }
}
