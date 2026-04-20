package com.dass.ims.ui.studentdetails

import androidx.lifecycle.ViewModel
import com.dass.ims.data.repository.StudentRepository
import com.dass.ims.data.repository.GuardianRepository
import com.dass.ims.data.repository.EnrollmentRepository

class StudentProfileViewModel(
    val students: StudentRepository,
    val guardians: GuardianRepository,
    val enrollments: EnrollmentRepository
) : ViewModel()
