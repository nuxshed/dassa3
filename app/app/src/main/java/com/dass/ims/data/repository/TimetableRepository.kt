package com.dass.ims.data.repository

import com.dass.ims.data.local.dao.TimetableDao
import com.dass.ims.data.local.entity.TimetableEntity

class TimetableRepository(private val dao: TimetableDao) {
    suspend fun getactive() = dao.getactive()
    suspend fun getall() = dao.getall()
    suspend fun getbyid(id: Long) = dao.getbyid(id)
    suspend fun insert(timetable: TimetableEntity) = dao.insert(timetable)
    suspend fun update(timetable: TimetableEntity) = dao.update(timetable)
}
