package com.dass.ims.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dass.ims.data.local.StudentWithUser
import com.dass.ims.data.local.entity.BatchEntity
import com.dass.ims.data.local.entity.SubjectEntity
import com.dass.ims.data.repository.BatchRepository
import com.dass.ims.data.repository.StudentRepository
import com.dass.ims.data.repository.SubjectRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class SearchState(
    val query: String = "",
    val students: List<StudentWithUser> = emptyList(),
    val batches: List<BatchEntity> = emptyList(),
    val subjects: List<SubjectEntity> = emptyList(),
)

class SearchViewModel(
    val students: StudentRepository,
    val batches: BatchRepository,
    val subjects: SubjectRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(SearchState())
    val state: StateFlow<SearchState> = _state

    fun search(q: String) {
        _state.value = _state.value.copy(query = q)
        if (q.length < 2) {
            _state.value = _state.value.copy(students = emptyList(), batches = emptyList(), subjects = emptyList())
            return
        }
        viewModelScope.launch {
            val matchedStudents = students.search(q)
            val allBatches = batches.getall().filter { it.name.contains(q, ignoreCase = true) }
            val allSubjects = subjects.getall().filter { it.name.contains(q, ignoreCase = true) || it.code.contains(q, ignoreCase = true) }
            _state.value = _state.value.copy(students = matchedStudents, batches = allBatches, subjects = allSubjects)
        }
    }
}
