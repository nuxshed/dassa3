package com.dass.ims.ui.timetable

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.dass.ims.data.LocalRole
import com.dass.ims.data.UserRole
import com.dass.ims.data.local.SlotWithDetails
import com.dass.ims.navigation.Routes
import com.dass.ims.ui.ViewModelFactory
import com.dass.ims.ui.shared.BottomNavBar
import com.dass.ims.ui.shared.NavTab
import com.dass.ims.ui.shared.SlotPill
import com.dass.ims.ui.theme.ImsIcon
import com.dass.ims.ui.theme.ImsIcons
import com.dass.ims.ui.theme.ImsTheme
import com.dass.ims.ui.theme.InterFamily
import com.dass.ims.ui.theme.JetBrainsMonoFamily
import kotlinx.coroutines.launch

private val days = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
private val periods = listOf("8:30 – 9:55", "10:05 – 11:30", "11:40 – 1:05", "2:00 – 3:25", "3:35 – 5:00")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimetableScreen(navController: NavController) {
    val vm: TimetableViewModel = viewModel(factory = ViewModelFactory(LocalContext.current))
    val state by vm.state.collectAsState()
    val role = LocalRole.current
    val isadmin = role.role == UserRole.ADMIN
    val colors = ImsTheme.colors

    LaunchedEffect(Unit) { vm.load() }

    var gridview by remember { mutableStateOf(true) }
    var selectedCell by remember { mutableStateOf<Pair<Int, Int>?>(null) }
    val sheetstate = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize().background(colors.bg).statusBarsPadding()) {
        timetableheader(
            timetable = state.timetable?.name ?: "No timetable",
            gridview = gridview,
            ontoggle = { gridview = it },
        )

        if (isadmin && state.warnings.isNotEmpty()) {
            alertbanner(state.warnings.first())
        }

        if (state.loading) {
            Box(modifier = Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                Text("Loading…", color = colors.textfaint, fontFamily = InterFamily, fontSize = 13.sp)
            }
        } else if (gridview) {
            Column(modifier = Modifier.weight(1f)) {
                timetablegrid(
                    slots = state.slots,
                    warnings = state.warnings,
                    isadmin = isadmin,
                    oncellclick = { day, period ->
                        selectedCell = Pair(day, period)
                        scope.launch { sheetstate.show() }
                    },
                    modifier = Modifier
                        .padding(horizontal = 12.dp)
                        .padding(top = 10.dp),
                )
                if (isadmin) {
                    admintray(
                        onclick = { navController.navigate(Routes.timetablecreate(0, 0)) },
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
                    )
                }
            }
        } else {
            listview(
                slots = state.slots,
                warnings = state.warnings,
                isadmin = isadmin,
                onedit = { slot -> navController.navigate(Routes.timetableedit(slot.slotid)) },
                ondelete = { slot -> vm.deleteslot(slot) },
                modifier = Modifier.weight(1f),
            )
        }

        BottomNavBar(
            active = NavTab.SCHEDULE,
            ontabclick = { tab ->
                when (tab) {
                    NavTab.HOME -> navController.navigate(Routes.DASHBOARD)
                    NavTab.SEARCH -> navController.navigate(Routes.SEARCH)
                    NavTab.SCHEDULE -> {}
                    NavTab.PEOPLE -> navController.navigate(Routes.STUDENTS)
                    NavTab.ME -> navController.navigate(Routes.stub("profile"))
                }
            },
        )
    }

    val cell = selectedCell
    if (cell != null) {
        val cellslots = state.slots.filter { it.day == cell.first && it.period == cell.second }
        ModalBottomSheet(
            onDismissRequest = { selectedCell = null },
            sheetState = sheetstate,
            containerColor = colors.bg,
            tonalElevation = 0.dp,
        ) {
            cellsheet(
                day = cell.first,
                period = cell.second,
                slots = cellslots,
                isadmin = isadmin,
                warnings = state.warnings,
                oncreate = {
                    selectedCell = null
                    navController.navigate(Routes.timetablecreate(cell.first, cell.second))
                },
                onedit = { slot ->
                    selectedCell = null
                    navController.navigate(Routes.timetableedit(slot.slotid))
                },
                ondelete = { slot ->
                    selectedCell = null
                    vm.deleteslot(slot)
                },
            )
        }
    }
}

@Composable
private fun timetableheader(
    timetable: String,
    gridview: Boolean,
    ontoggle: (Boolean) -> Unit,
) {
    val colors = ImsTheme.colors
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Column {
            Text(
                text = "TIMETABLE",
                fontSize = 11.sp,
                color = colors.textsecondary,
                fontFamily = InterFamily,
                fontWeight = FontWeight.Normal,
                letterSpacing = 0.8.sp,
            )
            Text(
                text = timetable,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = colors.text,
                fontFamily = InterFamily,
                letterSpacing = (-0.4).sp,
            )
        }
        Row(
            modifier = Modifier
                .border(1.dp, colors.border, RoundedCornerShape(6.dp))
                .background(colors.surface, RoundedCornerShape(6.dp))
                .clip(RoundedCornerShape(6.dp)),
        ) {
            togglebtn(active = gridview, onclick = { ontoggle(true) }) {
                ImsIcon(icon = ImsIcons.grip, size = 16.dp, tint = if (gridview) colors.text else colors.textfaint)
            }
            togglebtn(active = !gridview, onclick = { ontoggle(false) }) {
                ImsIcon(icon = ImsIcons.filter, size = 16.dp, tint = if (!gridview) colors.text else colors.textfaint)
            }
        }
    }
    Divider(color = colors.border, thickness = 1.dp)
}

@Composable
private fun togglebtn(active: Boolean, onclick: () -> Unit, content: @Composable () -> Unit) {
    val colors = ImsTheme.colors
    Box(
        modifier = Modifier
            .background(if (active) colors.selected else colors.surface)
            .clickable { onclick() }
            .padding(horizontal = 10.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center,
    ) {
        content()
    }
}

@Composable
private fun alertbanner(msg: String) {
    val colors = ImsTheme.colors
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(colors.alertbg)
            .border(1.dp, colors.alertborder)
            .padding(horizontal = 14.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        ImsIcon(icon = ImsIcons.alert, tint = colors.alertfg, size = 16.dp)
        Text(
            text = msg,
            fontSize = 12.sp,
            color = colors.alertfg,
            fontFamily = InterFamily,
            modifier = Modifier.weight(1f),
        )
    }
}

@Composable
private fun timetablegrid(
    slots: List<SlotWithDetails>,
    warnings: List<String>,
    isadmin: Boolean,
    oncellclick: (Int, Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = ImsTheme.colors
    val warnedcodes = warnings.map { it.substringBefore(" ") }.toSet()

    Box(
        modifier = modifier
            .border(1.dp, colors.border, RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp)),
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colors.surface)
                    .height(36.dp),
            ) {
                Box(modifier = Modifier.width(40.dp).height(36.dp))
                days.forEach { day ->
                    Box(
                        modifier = Modifier.weight(1f).height(36.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = day.uppercase(),
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = colors.textsecondary,
                            fontFamily = InterFamily,
                            textAlign = TextAlign.Center,
                        )
                    }
                }
            }
            Divider(color = colors.border, thickness = 1.dp)

            periods.forEachIndexed { pi, period ->
                Row(modifier = Modifier.fillMaxWidth()) {
                    val parts = period.split(" – ")
                    Box(
                        modifier = Modifier
                            .width(40.dp)
                            .background(colors.surface)
                            .border(0.dp, colors.border)
                            .padding(end = 4.dp, top = 4.dp, bottom = 4.dp),
                        contentAlignment = Alignment.TopCenter,
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = parts.getOrElse(0) { "" },
                                fontSize = 8.sp,
                                color = colors.textfaint,
                                fontFamily = JetBrainsMonoFamily,
                                textAlign = TextAlign.Center,
                            )
                            Text(
                                text = parts.getOrElse(1) { "" },
                                fontSize = 8.sp,
                                color = colors.textfaint,
                                fontFamily = JetBrainsMonoFamily,
                                textAlign = TextAlign.Center,
                            )
                        }
                    }
                    Box(modifier = Modifier.width(1.dp).height(60.dp).background(colors.border))
                    days.indices.forEach { di ->
                        val cellslots = slots.filter { it.day == di && it.period == pi }
                        gridcell(
                            slots = cellslots,
                            isadmin = isadmin,
                            warnedcodes = warnedcodes,
                            onclick = { oncellclick(di, pi) },
                            modifier = Modifier.weight(1f),
                        )
                        if (di < days.size - 1) {
                            Box(modifier = Modifier.width(1.dp).height(60.dp).background(colors.border))
                        }
                    }
                }
                if (pi < periods.size - 1) {
                    Divider(color = colors.border, thickness = 1.dp)
                }
            }
        }
    }
}

@Composable
private fun gridcell(
    slots: List<SlotWithDetails>,
    isadmin: Boolean,
    warnedcodes: Set<String>,
    onclick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = ImsTheme.colors
    Box(
        modifier = modifier
            .height(60.dp)
            .clickable { onclick() }
            .padding(3.dp),
        contentAlignment = Alignment.TopStart,
    ) {
        if (slots.isEmpty()) {
            if (isadmin) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .border(1.dp, colors.border, RoundedCornerShape(50)),
                        contentAlignment = Alignment.Center,
                    ) {
                        ImsIcon(icon = ImsIcons.plus, size = 12.dp, tint = colors.textfaint)
                    }
                }
            }
        } else {
            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                slots.forEach { slot ->
                    SlotPill(
                        code = slot.subjectcode,
                        warn = slot.subjectcode in warnedcodes,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }
        }
    }
}

@Composable
private fun admintray(onclick: () -> Unit, modifier: Modifier = Modifier) {
    val colors = ImsTheme.colors
    Row(
        modifier = modifier.horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "Slots",
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold,
            color = colors.textsecondary,
            fontFamily = InterFamily,
            letterSpacing = 0.6.sp,
        )
        Box(
            modifier = Modifier
                .border(1.dp, colors.border, RoundedCornerShape(6.dp))
                .clickable { onclick() }
                .padding(horizontal = 12.dp, vertical = 8.dp),
            contentAlignment = Alignment.Center,
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                ImsIcon(icon = ImsIcons.plus, size = 14.dp, tint = colors.textfaint)
                Text(
                    text = "Add slot",
                    fontSize = 12.sp,
                    color = colors.textsecondary,
                    fontFamily = InterFamily,
                )
            }
        }
    }
}

@Composable
private fun listview(
    slots: List<SlotWithDetails>,
    warnings: List<String>,
    isadmin: Boolean,
    onedit: (SlotWithDetails) -> Unit,
    ondelete: (SlotWithDetails) -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = ImsTheme.colors
    val warnedcodes = warnings.map { it.substringBefore(" ") }.toSet()
    val sorted = slots.sortedWith(compareBy({ it.day }, { it.period }))

    LazyColumn(modifier = modifier) {
        items(sorted) { slot ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                SlotPill(code = slot.subjectcode, warn = slot.subjectcode in warnedcodes)
                Spacer(Modifier.width(10.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = slot.subjectname,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = colors.text,
                        fontFamily = InterFamily,
                    )
                    Text(
                        text = "${days.getOrElse(slot.day) { "?" }} · ${periods.getOrElse(slot.period) { "?" }} · ${slot.room}",
                        fontSize = 11.sp,
                        color = colors.textsecondary,
                        fontFamily = InterFamily,
                    )
                }
                if (isadmin) {
                    ImsIcon(
                        icon = ImsIcons.pencil,
                        size = 16.dp,
                        tint = colors.textfaint,
                        modifier = Modifier.clickable { onedit(slot) }.padding(4.dp),
                    )
                    Spacer(Modifier.width(4.dp))
                    ImsIcon(
                        icon = ImsIcons.trash,
                        size = 16.dp,
                        tint = colors.textfaint,
                        modifier = Modifier.clickable { ondelete(slot) }.padding(4.dp),
                    )
                }
            }
            Divider(color = colors.divider, thickness = 1.dp, modifier = Modifier.padding(horizontal = 16.dp))
        }
    }
}

@Composable
private fun cellsheet(
    day: Int,
    period: Int,
    slots: List<SlotWithDetails>,
    isadmin: Boolean,
    warnings: List<String>,
    oncreate: () -> Unit,
    onedit: (SlotWithDetails) -> Unit,
    ondelete: (SlotWithDetails) -> Unit,
) {
    val colors = ImsTheme.colors
    val warnedcodes = warnings.map { it.substringBefore(" ") }.toSet()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .padding(bottom = 32.dp),
    ) {
        Text(
            text = "${days.getOrElse(day) { "?" }}, ${periods.getOrElse(period) { "?" }}",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = colors.text,
            fontFamily = InterFamily,
            letterSpacing = (-0.3).sp,
        )
        Spacer(Modifier.height(16.dp))

        if (slots.isEmpty()) {
            Text(
                text = "No classes scheduled",
                fontSize = 13.sp,
                color = colors.textfaint,
                fontFamily = InterFamily,
            )
        } else {
            slots.forEach { slot ->
                slotdetailrow(
                    slot = slot,
                    isadmin = isadmin,
                    warn = slot.subjectcode in warnedcodes,
                    onedit = { onedit(slot) },
                    ondelete = { ondelete(slot) },
                )
                Spacer(Modifier.height(10.dp))
            }
        }

        if (isadmin) {
            Spacer(Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, colors.border, RoundedCornerShape(8.dp))
                    .clickable { oncreate() }
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                ImsIcon(icon = ImsIcons.plus, size = 16.dp, tint = colors.accent)
                Text(
                    text = "Add another class here",
                    fontSize = 13.sp,
                    color = colors.accent,
                    fontFamily = InterFamily,
                )
            }
        }
    }
}

@Composable
private fun slotdetailrow(
    slot: SlotWithDetails,
    isadmin: Boolean,
    warn: Boolean,
    onedit: () -> Unit,
    ondelete: () -> Unit,
) {
    val colors = ImsTheme.colors
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, if (warn) colors.alertborder else colors.border, RoundedCornerShape(8.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        SlotPill(code = slot.subjectcode, warn = warn)
        Spacer(Modifier.width(10.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = slot.subjectname,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = colors.text,
                fontFamily = InterFamily,
            )
            Text(
                text = "${slot.employeename} · ${slot.room} · ${slot.type}",
                fontSize = 11.sp,
                color = colors.textsecondary,
                fontFamily = InterFamily,
            )
            if (slot.batchname.isNotEmpty()) {
                Text(
                    text = slot.batchname,
                    fontSize = 10.sp,
                    color = colors.textfaint,
                    fontFamily = InterFamily,
                )
            }
        }
        if (isadmin) {
            ImsIcon(
                icon = ImsIcons.pencil,
                size = 16.dp,
                tint = colors.textfaint,
                modifier = Modifier.clickable { onedit() }.padding(4.dp),
            )
            Spacer(Modifier.width(4.dp))
            ImsIcon(
                icon = ImsIcons.trash,
                size = 16.dp,
                tint = colors.textfaint,
                modifier = Modifier.clickable { ondelete() }.padding(4.dp),
            )
        }
    }
}
