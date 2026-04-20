package com.dass.ims.ui.timetable

import androidx.lifecycle.ViewModel
import com.dass.ims.data.repository.SlotRepository
import com.dass.ims.data.repository.SubjectRepository
import com.dass.ims.data.repository.EmployeeRepository
import com.dass.ims.data.repository.BatchRepository

class SlotEditViewModel(
    val slots: SlotRepository,
    val subjects: SubjectRepository,
    val employees: EmployeeRepository,
    val batches: BatchRepository
) : ViewModel()
