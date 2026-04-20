package com.dass.ims.ui.studentdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dass.ims.data.local.StudentWithUser
import com.dass.ims.data.local.entity.BatchEntity
import com.dass.ims.data.repository.BatchRepository
import com.dass.ims.data.repository.StudentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class StudentListState(
    val all: List<StudentWithUser> = emptyList(),
    val filtered: List<StudentWithUser> = emptyList(),
    val batches: List<BatchEntity> = emptyList(),
    val query: String = "",
    val filterbatch: String? = null,
    val filtergender: String? = null,
    val filtercategory: String? = null,
    val mingpa: Float = 0f,
    val maxgpa: Float = 10f,
    val loading: Boolean = true,
)

class StudentListViewModel(
    val students: StudentRepository,
    val batches: BatchRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(StudentListState())
    val state: StateFlow<StudentListState> = _state

    fun load() {
        viewModelScope.launch {
            val allStudents = students.getall()
            val allBatches = batches.getall()
            _state.value = StudentListState(all = allStudents, filtered = allStudents, batches = allBatches, loading = false)
        }
    }

    fun search(q: String) {
        _state.value = _state.value.copy(query = q)
        applyfilters()
    }

    fun setfilterbatch(b: String?) { _state.value = _state.value.copy(filterbatch = b); applyfilters() }
    fun setfiltergender(g: String?) { _state.value = _state.value.copy(filtergender = g); applyfilters() }
    fun setfiltercategory(c: String?) { _state.value = _state.value.copy(filtercategory = c); applyfilters() }
    fun setgparange(min: Float, max: Float) { _state.value = _state.value.copy(mingpa = min, maxgpa = max); applyfilters() }

    fun resetfilters() {
        _state.value = _state.value.copy(filterbatch = null, filtergender = null, filtercategory = null, mingpa = 0f, maxgpa = 10f)
        applyfilters()
    }

    private fun applyfilters() {
        val s = _state.value
        val result = s.all.filter { student ->
            (s.query.isEmpty() || student.name.contains(s.query, true) || student.roll.contains(s.query, true)) &&
            (s.filterbatch == null || student.batchname == s.filterbatch) &&
            (s.filtergender == null || student.gender == s.filtergender) &&
            (s.filtercategory == null || student.category == s.filtercategory) &&
            student.gpa >= s.mingpa && student.gpa <= s.maxgpa
        }
        _state.value = s.copy(filtered = result)
    }
}
