package com.dass.ims.ui.timetable

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.dass.ims.ui.ViewModelFactory
import com.dass.ims.ui.theme.ImsIcon
import com.dass.ims.ui.theme.ImsIcons
import com.dass.ims.ui.theme.ImsTheme
import com.dass.ims.ui.theme.InterFamily

private val daynames = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
private val periodnames = listOf("8:30", "10:05", "11:40", "2:00", "3:35")
private val slottypes = listOf("lecture", "tutorial", "lab", "exam")

@Composable
fun SlotEditScreen(navController: NavController, day: Int, period: Int, slotid: Long) {
    val vm: SlotEditViewModel = viewModel(factory = ViewModelFactory(LocalContext.current))
    val state by vm.state.collectAsState()
    val colors = ImsTheme.colors
    val isedit = slotid > 0

    LaunchedEffect(Unit) { vm.load() }
    LaunchedEffect(state.saved) { if (state.saved) navController.popBackStack() }

    var showsubjectpicker by remember { mutableStateOf(false) }
    var showemployeepicker by remember { mutableStateOf(false) }
    var showbatchpicker by remember { mutableStateOf(false) }

    val daystr = if (day >= 0) daynames.getOrElse(day) { "?" } else "?"
    val periodstr = if (period >= 0) periodnames.getOrElse(period) { "?" } else "?"

    Column(modifier = Modifier.fillMaxSize().background(colors.bg).statusBarsPadding()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(width = 1.dp, color = colors.border)
                .padding(horizontal = 16.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = "Cancel",
                fontSize = 14.sp,
                color = colors.textsecondary,
                fontFamily = InterFamily,
                modifier = Modifier.clickable { navController.popBackStack() },
            )
            Text(
                text = if (isedit) "Edit slot" else "New slot",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = colors.text,
                fontFamily = InterFamily,
            )
            Text(
                text = "Save",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = colors.accent,
                fontFamily = InterFamily,
                modifier = Modifier.clickable { vm.save(day, period) },
            )
        }

        if (state.loading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Loading…", color = colors.textfaint, fontFamily = InterFamily, fontSize = 13.sp)
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 18.dp, start = 20.dp, end = 20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                selectorfield(
                    label = "SUBJECT",
                    value = state.selectedsubject?.name ?: "Select subject",
                    onclick = { showsubjectpicker = true },
                )
                selectorfield(
                    label = "FACULTY",
                    value = state.selectedemployee?.designation ?: "Select faculty",
                    onclick = { showemployeepicker = true },
                )
                Column {
                    Text(
                        text = "ROOM",
                        fontSize = 11.sp,
                        color = colors.textsecondary,
                        fontFamily = InterFamily,
                        letterSpacing = 0.6.sp,
                        modifier = Modifier.padding(bottom = 5.dp),
                    )
                    OutlinedTextField(
                        value = state.room,
                        onValueChange = { vm.setroom(it) },
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = androidx.compose.ui.text.TextStyle(
                            fontSize = 14.sp,
                            fontFamily = InterFamily,
                            color = colors.text,
                        ),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = colors.accent,
                            unfocusedBorderColor = colors.border,
                            focusedContainerColor = colors.surface,
                            unfocusedContainerColor = colors.surface,
                        ),
                        shape = RoundedCornerShape(8.dp),
                        singleLine = true,
                    )
                }
                selectorfield(
                    label = "BATCH",
                    value = state.selectedbatch?.name ?: "Select batch",
                    onclick = { showbatchpicker = true },
                )

                Column {
                    Text(
                        text = "TYPE",
                        fontSize = 11.sp,
                        color = colors.textsecondary,
                        fontFamily = InterFamily,
                        letterSpacing = 0.6.sp,
                        modifier = Modifier.padding(bottom = 5.dp),
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        slottypes.forEach { t ->
                            val selected = state.type == t
                            Box(
                                modifier = Modifier
                                    .border(
                                        width = 1.dp,
                                        color = if (selected) colors.accent else colors.border,
                                        shape = RoundedCornerShape(6.dp),
                                    )
                                    .background(
                                        color = if (selected) colors.accent else colors.bg,
                                        shape = RoundedCornerShape(6.dp),
                                    )
                                    .clip(RoundedCornerShape(6.dp))
                                    .clickable { vm.settype(t) }
                                    .padding(horizontal = 12.dp, vertical = 6.dp),
                                contentAlignment = Alignment.Center,
                            ) {
                                Text(
                                    text = t.replaceFirstChar { it.uppercase() },
                                    fontSize = 13.sp,
                                    color = if (selected) colors.bg else colors.text,
                                    fontFamily = InterFamily,
                                )
                            }
                        }
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(colors.infobg, RoundedCornerShape(6.dp))
                        .border(1.dp, colors.infoborder, RoundedCornerShape(6.dp))
                        .padding(horizontal = 12.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    ImsIcon(icon = ImsIcons.alert, tint = colors.infofg, size = 14.dp)
                    Text(
                        text = "Day & period pre-filled from selected cell ($daystr · $periodstr).",
                        fontSize = 12.sp,
                        color = colors.infofg,
                        fontFamily = InterFamily,
                        modifier = Modifier.weight(1f),
                    )
                }
            }
        }
    }

    if (showsubjectpicker) {
        AlertDialog(
            onDismissRequest = { showsubjectpicker = false },
            title = { Text("Select Subject", fontFamily = InterFamily) },
            text = {
                LazyColumn {
                    items(state.subjects) { sub ->
                        Text(
                            text = sub.name,
                            fontFamily = InterFamily,
                            fontSize = 14.sp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { vm.selectsubject(sub); showsubjectpicker = false }
                                .padding(vertical = 8.dp, horizontal = 4.dp),
                        )
                        Divider(color = ImsTheme.colors.divider)
                    }
                }
            },
            confirmButton = {},
        )
    }

    if (showemployeepicker) {
        AlertDialog(
            onDismissRequest = { showemployeepicker = false },
            title = { Text("Select Faculty", fontFamily = InterFamily) },
            text = {
                LazyColumn {
                    items(state.employees) { emp ->
                        Text(
                            text = emp.designation,
                            fontFamily = InterFamily,
                            fontSize = 14.sp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { vm.selectemployee(emp); showemployeepicker = false }
                                .padding(vertical = 8.dp, horizontal = 4.dp),
                        )
                        Divider(color = ImsTheme.colors.divider)
                    }
                }
            },
            confirmButton = {},
        )
    }

    if (showbatchpicker) {
        AlertDialog(
            onDismissRequest = { showbatchpicker = false },
            title = { Text("Select Batch", fontFamily = InterFamily) },
            text = {
                LazyColumn {
                    items(state.batches) { batch ->
                        Text(
                            text = batch.name,
                            fontFamily = InterFamily,
                            fontSize = 14.sp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { vm.selectbatch(batch); showbatchpicker = false }
                                .padding(vertical = 8.dp, horizontal = 4.dp),
                        )
                        Divider(color = ImsTheme.colors.divider)
                    }
                }
            },
            confirmButton = {},
        )
    }
}

@Composable
private fun selectorfield(label: String, value: String, onclick: () -> Unit) {
    val colors = ImsTheme.colors
    Column {
        Text(
            text = label,
            fontSize = 11.sp,
            color = colors.textsecondary,
            fontFamily = InterFamily,
            letterSpacing = 0.6.sp,
            modifier = Modifier.padding(bottom = 5.dp),
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(colors.surface, RoundedCornerShape(8.dp))
                .border(1.dp, colors.border, RoundedCornerShape(8.dp))
                .clip(RoundedCornerShape(8.dp))
                .clickable { onclick() }
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = value,
                fontSize = 14.sp,
                color = colors.text,
                fontFamily = InterFamily,
                modifier = Modifier.weight(1f),
            )
            ImsIcon(icon = ImsIcons.chevright, size = 16.dp, tint = colors.textfaint)
        }
    }
}
