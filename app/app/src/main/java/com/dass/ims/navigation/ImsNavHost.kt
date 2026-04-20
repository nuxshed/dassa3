package com.dass.ims.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dass.ims.ui.dashboard.DashboardScreen
import com.dass.ims.ui.login.LoginScreen
import com.dass.ims.ui.search.SearchScreen
import com.dass.ims.ui.stub.StubScreen
import com.dass.ims.ui.studentdetails.StudentListScreen
import com.dass.ims.ui.studentdetails.StudentProfileScreen
import com.dass.ims.ui.timetable.SlotEditScreen
import com.dass.ims.ui.timetable.TimetableScreen

object Routes {
    const val LOGIN = "login"
    const val DASHBOARD = "dashboard"
    const val SEARCH = "search"
    const val TIMETABLE = "timetable"
    const val TIMETABLE_CREATE = "timetable/create/{day}/{period}"
    const val TIMETABLE_EDIT = "timetable/edit/{slotid}"
    const val STUDENTS = "students"
    const val STUDENT_PROFILE = "students/{studentid}"
    const val STUB = "stub/{module}"

    fun timetablecreate(day: Int, period: Int) = "timetable/create/$day/$period"
    fun timetableedit(slotid: Long) = "timetable/edit/$slotid"
    fun studentprofile(studentid: Long) = "students/$studentid"
    fun stub(module: String) = "stub/$module"
}

@Composable
fun ImsNavHost() {
    val nav = rememberNavController()

    NavHost(navController = nav, startDestination = Routes.LOGIN) {
        composable(Routes.LOGIN) {
            LoginScreen(onlogin = {
                nav.navigate(Routes.DASHBOARD) {
                    popUpTo(Routes.LOGIN) { inclusive = true }
                }
            })
        }
        composable(Routes.DASHBOARD) { DashboardScreen(nav) }
        composable(Routes.SEARCH) { SearchScreen(nav) }
        composable(Routes.TIMETABLE) { TimetableScreen(nav) }
        composable(
            Routes.TIMETABLE_CREATE,
            arguments = listOf(
                navArgument("day") { type = NavType.IntType },
                navArgument("period") { type = NavType.IntType },
            )
        ) { back ->
            SlotEditScreen(nav, back.arguments!!.getInt("day"), back.arguments!!.getInt("period"), -1L)
        }
        composable(
            Routes.TIMETABLE_EDIT,
            arguments = listOf(navArgument("slotid") { type = NavType.LongType })
        ) { back ->
            SlotEditScreen(nav, -1, -1, back.arguments!!.getLong("slotid"))
        }
        composable(Routes.STUDENTS) { StudentListScreen(nav) }
        composable(
            Routes.STUDENT_PROFILE,
            arguments = listOf(navArgument("studentid") { type = NavType.LongType })
        ) { back ->
            StudentProfileScreen(nav, back.arguments!!.getLong("studentid"))
        }
        composable(
            Routes.STUB,
            arguments = listOf(navArgument("module") { type = NavType.StringType })
        ) { back ->
            StubScreen(nav, back.arguments!!.getString("module") ?: "")
        }
    }
}
