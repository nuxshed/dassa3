package com.dass.ims.ui.timetable

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dass.ims.data.local.SlotWithDetails
import com.dass.ims.data.local.entity.SlotEntity
import com.dass.ims.data.local.entity.TimetableEntity
import com.dass.ims.data.repository.EmployeeRepository
import com.dass.ims.data.repository.SlotRepository
import com.dass.ims.data.repository.SubjectRepository
import com.dass.ims.data.repository.TimetableRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class TimetableState(
    val timetable: TimetableEntity? = null,
    val slots: List<SlotWithDetails> = emptyList(),
    val warnings: List<String> = emptyList(),
    val loading: Boolean = true,
)

class TimetableViewModel(
    val timetables: TimetableRepository,
    val slots: SlotRepository,
    val subjects: SubjectRepository,
    val employees: EmployeeRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(TimetableState())
    val state: StateFlow<TimetableState> = _state

    fun load() {
        viewModelScope.launch {
            val tt = timetables.getactive()
            if (tt == null) { _state.value = TimetableState(loading = false); return@launch }
            val slotList = slots.getbytimetable(tt.id)
            val warnings = slotList.groupBy { it.subjectcode }
                .filter { it.value.size > 3 }
                .map { "${it.key} exceeds weekly limit (${it.value.size}/3)" }
            _state.value = TimetableState(timetable = tt, slots = slotList, warnings = warnings, loading = false)
        }
    }

    fun deleteslot(slot: SlotWithDetails) {
        viewModelScope.launch {
            val entity = SlotEntity(
                id = slot.slotid,
                timetableid = slot.timetableid,
                subjectid = slot.subjectid,
                employeeid = slot.employeeid,
                day = slot.day,
                period = slot.period,
                room = slot.room,
                type = slot.type,
                isrecurring = slot.isrecurring,
                batchname = slot.batchname,
            )
            slots.delete(entity)
            load()
        }
    }
}
