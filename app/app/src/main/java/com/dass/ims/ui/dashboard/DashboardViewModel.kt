package com.dass.ims.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dass.ims.data.local.entity.UserEntity
import com.dass.ims.data.local.entity.NewsEntity
import com.dass.ims.data.local.SlotWithDetails
import com.dass.ims.data.local.EnrollmentWithSubject
import com.dass.ims.data.local.StudentWithUser
import com.dass.ims.data.repository.UserRepository
import com.dass.ims.data.repository.NewsRepository
import com.dass.ims.data.repository.EnrollmentRepository
import com.dass.ims.data.repository.StudentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class DashboardState(
    val user: UserEntity? = null,
    val student: StudentWithUser? = null,
    val news: List<NewsEntity> = emptyList(),
    val enrollments: List<EnrollmentWithSubject> = emptyList(),
    val nextslot: SlotWithDetails? = null,
)

class DashboardViewModel(
    val users: UserRepository,
    val news: NewsRepository,
    val enrollments: EnrollmentRepository,
    val students: StudentRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(DashboardState())
    val state: StateFlow<DashboardState> = _state

    fun load(userid: Long, isadmin: Boolean) {
        viewModelScope.launch {
            val user = users.getbyid(userid)
            val newslist = news.getall()
            if (isadmin) {
                _state.value = DashboardState(user = user, news = newslist)
            } else {
                val allstudents = students.getall()
                val student = allstudents.firstOrNull { it.userid == userid }
                val enrollmentlist = if (student != null) enrollments.getbystudent(student.studentid) else emptyList()
                _state.value = DashboardState(user = user, student = student, news = newslist, enrollments = enrollmentlist)
            }
        }
    }
}
