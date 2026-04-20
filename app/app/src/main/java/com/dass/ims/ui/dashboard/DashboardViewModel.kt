package com.dass.ims.ui.dashboard

import androidx.lifecycle.ViewModel
import com.dass.ims.data.repository.UserRepository
import com.dass.ims.data.repository.NewsRepository
import com.dass.ims.data.repository.EnrollmentRepository
import com.dass.ims.data.repository.StudentRepository

class DashboardViewModel(
    val users: UserRepository,
    val news: NewsRepository,
    val enrollments: EnrollmentRepository,
    val students: StudentRepository
) : ViewModel()
