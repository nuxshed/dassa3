package com.dass.ims.ui.timetable

import androidx.lifecycle.ViewModel
import com.dass.ims.data.repository.TimetableRepository
import com.dass.ims.data.repository.SlotRepository
import com.dass.ims.data.repository.SubjectRepository
import com.dass.ims.data.repository.EmployeeRepository

class TimetableViewModel(
    val timetables: TimetableRepository,
    val slots: SlotRepository,
    val subjects: SubjectRepository,
    val employees: EmployeeRepository
) : ViewModel()
