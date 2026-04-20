package com.dass.ims.data.repository

import com.dass.ims.data.local.dao.SlotDao
import com.dass.ims.data.local.entity.SlotEntity

class SlotRepository(private val dao: SlotDao) {
    suspend fun getbytimetable(ttid: Long) = dao.getbytimetable(ttid)
    suspend fun insert(slot: SlotEntity) = dao.insert(slot)
    suspend fun update(slot: SlotEntity) = dao.update(slot)
    suspend fun delete(slot: SlotEntity) = dao.delete(slot)
    suspend fun checksubjectlimit(ttid: Long, subid: Long, limit: Int = 3): Boolean =
        dao.countsubjectslots(ttid, subid) >= limit
    suspend fun checkemployeeworkload(ttid: Long, empid: Long, limit: Int = 6): Boolean =
        dao.countemployeeslots(ttid, empid) >= limit
}
