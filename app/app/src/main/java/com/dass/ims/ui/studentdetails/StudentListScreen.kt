package com.dass.ims.ui.studentdetails

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.dass.ims.data.local.StudentWithUser
import com.dass.ims.navigation.Routes
import com.dass.ims.ui.ViewModelFactory
import com.dass.ims.ui.shared.Avatar
import com.dass.ims.ui.shared.BottomNavBar
import com.dass.ims.ui.shared.NavTab
import com.dass.ims.ui.shared.SearchField
import com.dass.ims.ui.shared.TopBar
import com.dass.ims.ui.theme.ImsIcon
import com.dass.ims.ui.theme.ImsIcons
import com.dass.ims.ui.theme.ImsTheme
import com.dass.ims.ui.theme.InterFamily
import com.dass.ims.ui.theme.JetBrainsMonoFamily
import com.dass.ims.ui.theme.avatarcolors

private fun avatarcolor(userid: Long) = avatarcolors[(userid % avatarcolors.size).toInt()]

private fun initials(name: String) = name.split(" ")
    .mapNotNull { it.firstOrNull()?.uppercaseChar() }
    .take(2)
    .joinToString("")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentListScreen(navController: NavController) {
    val vm: StudentListViewModel = viewModel(factory = ViewModelFactory(LocalContext.current))
    val state by vm.state.collectAsState()
    val colors = ImsTheme.colors

    LaunchedEffect(Unit) { vm.load() }

    var showfilter by remember { mutableStateOf(false) }
    var showsearch by remember { mutableStateOf(false) }

    // pending filter state for sheet (applied on confirm)
    var pendingbatch by remember { mutableStateOf<String?>(null) }
    var pendinggender by remember { mutableStateOf<String?>(null) }
    var pendingcategory by remember { mutableStateOf<String?>(null) }
    var pendinggparange by remember { mutableStateOf(0f..10f) }

    val activefiltercount = listOfNotNull(
        state.filterbatch, state.filtergender, state.filtercategory,
        if (state.mingpa > 0f || state.maxgpa < 10f) "gpa" else null,
    ).size

    Column(modifier = Modifier.fillMaxSize().background(colors.bg)) {
        TopBar(
            leftslot = {
                Text(
                    text = "People",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = InterFamily,
                    color = colors.text,
                )
            },
            rightslot = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(14.dp),
                ) {
                    ImsIcon(
                        icon = ImsIcons.search,
                        size = 20.dp,
                        tint = colors.textsecondary,
                        modifier = Modifier.clickable { showsearch = !showsearch },
                    )
                    ImsIcon(
                        icon = ImsIcons.filter,
                        size = 20.dp,
                        tint = colors.textsecondary,
                        modifier = Modifier.clickable {
                            pendingbatch = state.filterbatch
                            pendinggender = state.filtergender
                            pendingcategory = state.filtercategory
                            pendinggparange = state.mingpa..state.maxgpa
                            showfilter = true
                        },
                    )
                }
            },
        )

        if (showsearch) {
            SearchField(
                value = state.query,
                onchange = { vm.search(it) },
                placeholder = "Search students…",
                compact = true,
            )
        }

        // title + count
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(top = 14.dp, bottom = 4.dp),
        ) {
            Text(
                text = "All students",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = InterFamily,
                color = colors.text,
                letterSpacing = (-0.5).sp,
            )
            Row {
                Text(
                    text = "",
                    fontSize = 12.sp,
                    color = colors.textsecondary,
                    fontFamily = InterFamily,
                )
                Text(
                    text = "${state.filtered.size}",
                    fontSize = 12.sp,
                    color = colors.textsecondary,
                    fontFamily = JetBrainsMonoFamily,
                )
                Text(
                    text = " result${if (state.filtered.size != 1) "s" else ""}",
                    fontSize = 12.sp,
                    color = colors.textsecondary,
                    fontFamily = InterFamily,
                )
                if (activefiltercount > 0) {
                    Text(
                        text = " · ",
                        fontSize = 12.sp,
                        color = colors.textsecondary,
                        fontFamily = InterFamily,
                    )
                    Text(
                        text = "$activefiltercount",
                        fontSize = 12.sp,
                        color = colors.textsecondary,
                        fontFamily = JetBrainsMonoFamily,
                    )
                    Text(
                        text = " filter${if (activefiltercount != 1) "s" else ""} active",
                        fontSize = 12.sp,
                        color = colors.textsecondary,
                        fontFamily = InterFamily,
                    )
                }
            }
        }

        // active filter chips
        if (activefiltercount > 0) {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp, horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                state.filterbatch?.let { b ->
                    item {
                        filterchip(label = b, ondismiss = { vm.setfilterbatch(null) })
                    }
                }
                state.filtergender?.let { g ->
                    item {
                        filterchip(label = g, ondismiss = { vm.setfiltergender(null) })
                    }
                }
                state.filtercategory?.let { c ->
                    item {
                        filterchip(label = c, ondismiss = { vm.setfiltercategory(null) })
                    }
                }
                if (state.mingpa > 0f || state.maxgpa < 10f) {
                    item {
                        filterchip(
                            label = "GPA ${state.mingpa.toInt()}–${state.maxgpa.toInt()}",
                            ondismiss = { vm.setgparange(0f, 10f) },
                        )
                    }
                }
            }
        }

        if (state.loading) {
            Box(
                modifier = Modifier.weight(1f).fillMaxWidth(),
                contentAlignment = Alignment.Center,
            ) {
                Text("Loading…", color = colors.textfaint, fontFamily = InterFamily, fontSize = 13.sp)
            }
        } else {
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(state.filtered) { s ->
                    studentrow(
                        student = s,
                        onclick = { navController.navigate(Routes.studentprofile(s.studentid)) },
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
                    NavTab.PEOPLE -> {}
                    NavTab.ME -> navController.navigate(Routes.stub("profile"))
                }
            },
        )
    }

    if (showfilter) {
        val sheetstate = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        val batchnames = state.batches.map { it.name }
        val genders = listOf("Male", "Female", "Other")
        val categories = state.all.map { it.category }.distinct().filter { it.isNotEmpty() }

        ModalBottomSheet(
            onDismissRequest = { showfilter = false },
            sheetState = sheetstate,
            containerColor = colors.bg,
            tonalElevation = 0.dp,
        ) {
            filtersheetcontent(
                batchnames = batchnames,
                genders = genders,
                categories = categories,
                selectedbatch = pendingbatch,
                selectedgender = pendinggender,
                selectedcategory = pendingcategory,
                gparange = pendinggparange,
                activefiltercount = activefiltercount,
                onselectbatch = { pendingbatch = if (pendingbatch == it) null else it },
                onselectgender = { pendinggender = if (pendinggender == it) null else it },
                onselectcategory = { pendingcategory = if (pendingcategory == it) null else it },
                ongparange = { pendinggparange = it },
                onreset = {
                    pendingbatch = null
                    pendinggender = null
                    pendingcategory = null
                    pendinggparange = 0f..10f
                    vm.resetfilters()
                    showfilter = false
                },
                onapply = {
                    vm.setfilterbatch(pendingbatch)
                    vm.setfiltergender(pendinggender)
                    vm.setfiltercategory(pendingcategory)
                    vm.setgparange(pendinggparange.start, pendinggparange.endInclusive)
                    showfilter = false
                },
            )
        }
    }
}

@Composable
private fun studentrow(student: StudentWithUser, onclick: () -> Unit) {
    val colors = ImsTheme.colors
    val ac = avatarcolor(student.userid)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onclick() }
            .padding(horizontal = 20.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Avatar(
            initials = initials(student.name),
            size = 36.dp,
            bg = ac.bg,
            fg = ac.fg,
        )
        Spacer(Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = student.name,
                fontSize = 14.sp,
                fontWeight = FontWeight.W500,
                fontFamily = InterFamily,
                color = colors.text,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Row {
                Text(
                    text = student.roll,
                    fontSize = 11.sp,
                    color = colors.textsecondary,
                    fontFamily = JetBrainsMonoFamily,
                )
                Text(
                    text = " · ${student.batchname}",
                    fontSize = 11.sp,
                    color = colors.textsecondary,
                    fontFamily = JetBrainsMonoFamily,
                )
            }
        }
        Spacer(Modifier.width(12.dp))
        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = "%.1f".format(student.gpa),
                fontSize = 12.sp,
                fontWeight = FontWeight.W500,
                fontFamily = JetBrainsMonoFamily,
                color = colors.text,
            )
            Text(
                text = student.category.ifEmpty { "—" },
                fontSize = 10.sp,
                color = colors.textfaint,
                fontFamily = InterFamily,
            )
        }
    }
    Divider(color = colors.divider, thickness = 1.dp)
}

@Composable
private fun filterchip(label: String, ondismiss: () -> Unit) {
    val colors = ImsTheme.colors
    Row(
        modifier = Modifier
            .background(colors.chipbg, RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))
            .padding(horizontal = 10.dp, vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Text(
            text = label,
            fontSize = 11.sp,
            fontFamily = InterFamily,
            color = colors.text,
        )
        ImsIcon(
            icon = ImsIcons.x,
            size = 12.dp,
            tint = colors.textfaint,
            modifier = Modifier.clickable { ondismiss() },
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun filtersheetcontent(
    batchnames: List<String>,
    genders: List<String>,
    categories: List<String>,
    selectedbatch: String?,
    selectedgender: String?,
    selectedcategory: String?,
    gparange: ClosedFloatingPointRange<Float>,
    activefiltercount: Int,
    onselectbatch: (String) -> Unit,
    onselectgender: (String) -> Unit,
    onselectcategory: (String) -> Unit,
    ongparange: (ClosedFloatingPointRange<Float>) -> Unit,
    onreset: () -> Unit,
    onapply: () -> Unit,
) {
    val colors = ImsTheme.colors

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .padding(bottom = 32.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        // drag handle
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ) {
            Box(
                modifier = Modifier
                    .width(36.dp)
                    .height(4.dp)
                    .background(colors.borderstrong, RoundedCornerShape(2.dp)),
            )
        }

        // title
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Filters",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = InterFamily,
                color = colors.text,
            )
            if (activefiltercount > 0) {
                Spacer(Modifier.width(6.dp))
                Text(
                    text = "($activefiltercount)",
                    fontSize = 14.sp,
                    fontFamily = JetBrainsMonoFamily,
                    color = colors.textsecondary,
                )
            }
        }

        // batch
        if (batchnames.isNotEmpty()) {
            filtergroup(label = "BATCH") {
                batchnames.forEach { b ->
                    selectchip(
                        label = b,
                        selected = selectedbatch == b,
                        onclick = { onselectbatch(b) },
                    )
                }
            }
        }

        // gender
        filtergroup(label = "GENDER") {
            genders.forEach { g ->
                selectchip(
                    label = g,
                    selected = selectedgender == g,
                    onclick = { onselectgender(g) },
                )
            }
        }

        // category
        if (categories.isNotEmpty()) {
            filtergroup(label = "CATEGORY") {
                categories.forEach { c ->
                    selectchip(
                        label = c,
                        selected = selectedcategory == c,
                        onclick = { onselectcategory(c) },
                    )
                }
            }
        }

        // GPA range
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "GPA",
                    fontSize = 11.sp,
                    fontFamily = InterFamily,
                    fontWeight = FontWeight.SemiBold,
                    color = colors.textsecondary,
                    letterSpacing = 0.6.sp,
                )
                Text(
                    text = "%.1f – %.1f".format(gparange.start, gparange.endInclusive),
                    fontSize = 12.sp,
                    fontFamily = JetBrainsMonoFamily,
                    color = colors.textsecondary,
                )
            }
            RangeSlider(
                value = gparange,
                onValueChange = { ongparange(it) },
                valueRange = 0f..10f,
                steps = 9,
                colors = SliderDefaults.colors(
                    thumbColor = colors.text,
                    activeTrackColor = colors.text,
                    inactiveTrackColor = colors.border,
                ),
            )
        }

        // buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .border(1.dp, colors.border, RoundedCornerShape(8.dp))
                    .background(colors.bg, RoundedCornerShape(8.dp))
                    .clip(RoundedCornerShape(8.dp))
                    .clickable { onreset() }
                    .padding(vertical = 12.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "Reset all",
                    fontSize = 13.sp,
                    fontFamily = InterFamily,
                    fontWeight = FontWeight.SemiBold,
                    color = colors.text,
                )
            }
            Box(
                modifier = Modifier
                    .weight(2f)
                    .background(colors.text, RoundedCornerShape(8.dp))
                    .clip(RoundedCornerShape(8.dp))
                    .clickable { onapply() }
                    .padding(vertical = 12.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "Apply filters",
                    fontSize = 13.sp,
                    fontFamily = InterFamily,
                    fontWeight = FontWeight.SemiBold,
                    color = colors.bg,
                )
            }
        }
    }
}

@Composable
private fun filtergroup(label: String, content: @Composable () -> Unit) {
    val colors = ImsTheme.colors
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = label,
            fontSize = 11.sp,
            fontFamily = InterFamily,
            fontWeight = FontWeight.SemiBold,
            color = colors.textsecondary,
            letterSpacing = 0.6.sp,
        )
        Row(
            modifier = Modifier.horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            content()
        }
    }
}

@Composable
private fun selectchip(label: String, selected: Boolean, onclick: () -> Unit) {
    val colors = ImsTheme.colors
    val shape = RoundedCornerShape(20.dp)
    Box(
        modifier = Modifier
            .then(
                if (selected)
                    Modifier.background(colors.text, shape)
                else
                    Modifier
                        .border(1.dp, colors.border, shape)
                        .background(colors.bg, shape)
            )
            .clip(shape)
            .clickable { onclick() }
            .padding(horizontal = 12.dp, vertical = 6.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = label,
            fontSize = 12.sp,
            fontFamily = InterFamily,
            color = if (selected) colors.bg else colors.text,
        )
    }
}
