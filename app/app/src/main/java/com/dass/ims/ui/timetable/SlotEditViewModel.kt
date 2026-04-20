package com.dass.ims.ui.timetable

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dass.ims.data.local.entity.BatchEntity
import com.dass.ims.data.local.entity.EmployeeEntity
import com.dass.ims.data.local.entity.SlotEntity
import com.dass.ims.data.local.entity.SubjectEntity
import com.dass.ims.data.repository.BatchRepository
import com.dass.ims.data.repository.EmployeeRepository
import com.dass.ims.data.repository.SlotRepository
import com.dass.ims.data.repository.SubjectRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class SlotEditState(
    val subjects: List<SubjectEntity> = emptyList(),
    val employees: List<EmployeeEntity> = emptyList(),
    val batches: List<BatchEntity> = emptyList(),
    val selectedsubject: SubjectEntity? = null,
    val selectedemployee: EmployeeEntity? = null,
    val selectedbatch: BatchEntity? = null,
    val room: String = "Room 101",
    val type: String = "lecture",
    val loading: Boolean = true,
    val saved: Boolean = false,
)

class SlotEditViewModel(
    val slots: SlotRepository,
    val subjects: SubjectRepository,
    val employees: EmployeeRepository,
    val batches: BatchRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(SlotEditState())
    val state: StateFlow<SlotEditState> = _state

    fun load() {
        viewModelScope.launch {
            val allsubjects = subjects.getall()
            val allemployees = employees.getall()
            val allbatches = batches.getall()
            _state.value = _state.value.copy(
                subjects = allsubjects,
                employees = allemployees,
                batches = allbatches,
                selectedsubject = allsubjects.firstOrNull(),
                selectedemployee = allemployees.firstOrNull(),
                selectedbatch = allbatches.firstOrNull(),
                loading = false,
            )
        }
    }

    fun selectsubject(s: SubjectEntity) { _state.value = _state.value.copy(selectedsubject = s) }
    fun selectemployee(e: EmployeeEntity) { _state.value = _state.value.copy(selectedemployee = e) }
    fun selectbatch(b: BatchEntity) { _state.value = _state.value.copy(selectedbatch = b) }
    fun setroom(r: String) { _state.value = _state.value.copy(room = r) }
    fun settype(t: String) { _state.value = _state.value.copy(type = t) }

    fun save(day: Int, period: Int) {
        val s = _state.value
        val subject = s.selectedsubject ?: return
        val employee = s.selectedemployee ?: return
        val batch = s.selectedbatch ?: return
        viewModelScope.launch {
            val slot = SlotEntity(
                timetableid = 1L,
                subjectid = subject.id,
                employeeid = employee.id,
                day = day,
                period = period,
                room = s.room,
                type = s.type,
                isrecurring = true,
                batchname = batch.name,
            )
            slots.insert(slot)
            _state.value = s.copy(saved = true)
        }
    }
}
