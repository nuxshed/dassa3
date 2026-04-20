package com.dass.ims.ui.studentdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dass.ims.data.local.EnrollmentWithSubject
import com.dass.ims.data.local.StudentWithUser
import com.dass.ims.data.local.entity.GuardianEntity
import com.dass.ims.data.repository.EnrollmentRepository
import com.dass.ims.data.repository.GuardianRepository
import com.dass.ims.data.repository.StudentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class StudentProfileState(
    val student: StudentWithUser? = null,
    val guardians: List<GuardianEntity> = emptyList(),
    val enrollments: List<EnrollmentWithSubject> = emptyList(),
    val loading: Boolean = true,
)

class StudentProfileViewModel(
    val students: StudentRepository,
    val guardians: GuardianRepository,
    val enrollments: EnrollmentRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(StudentProfileState())
    val state: StateFlow<StudentProfileState> = _state

    fun load(studentid: Long) {
        viewModelScope.launch {
            val student = students.getbyid(studentid)
            val guardianList = if (student != null) guardians.getbystudent(studentid) else emptyList()
            val enrollmentList = if (student != null) enrollments.getbystudent(studentid) else emptyList()
            _state.value = StudentProfileState(
                student = student,
                guardians = guardianList,
                enrollments = enrollmentList,
                loading = false,
            )
        }
    }
}
