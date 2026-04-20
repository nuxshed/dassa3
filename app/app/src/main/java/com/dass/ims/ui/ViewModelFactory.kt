package com.dass.ims.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dass.ims.ImsApp
import com.dass.ims.ui.dashboard.DashboardViewModel
import com.dass.ims.ui.search.SearchViewModel
import com.dass.ims.ui.timetable.TimetableViewModel
import com.dass.ims.ui.timetable.SlotEditViewModel
import com.dass.ims.ui.studentdetails.StudentListViewModel
import com.dass.ims.ui.studentdetails.StudentProfileViewModel

class ViewModelFactory(context: Context) : ViewModelProvider.Factory {
    private val c = (context.applicationContext as ImsApp).container

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T = when (modelClass) {
        DashboardViewModel::class.java -> DashboardViewModel(c.users, c.news, c.enrollments, c.students)
        SearchViewModel::class.java -> SearchViewModel(c.students, c.batches, c.subjects)
        TimetableViewModel::class.java -> TimetableViewModel(c.timetables, c.slots, c.subjects, c.employees)
        SlotEditViewModel::class.java -> SlotEditViewModel(c.slots, c.subjects, c.employees, c.batches)
        StudentListViewModel::class.java -> StudentListViewModel(c.students, c.batches)
        StudentProfileViewModel::class.java -> StudentProfileViewModel(c.students, c.guardians, c.enrollments)
        else -> throw IllegalArgumentException("Unknown ViewModel: ${modelClass.name}")
    } as T
}
