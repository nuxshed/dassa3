package com.dass.ims.ui.studentdetails

import androidx.lifecycle.ViewModel
import com.dass.ims.data.repository.StudentRepository
import com.dass.ims.data.repository.BatchRepository

class StudentListViewModel(
    val students: StudentRepository,
    val batches: BatchRepository
) : ViewModel()
