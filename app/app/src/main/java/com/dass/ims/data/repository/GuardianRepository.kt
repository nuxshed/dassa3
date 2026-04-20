package com.dass.ims.data.repository

import com.dass.ims.data.local.dao.GuardianDao
import com.dass.ims.data.local.entity.GuardianEntity

class GuardianRepository(private val dao: GuardianDao) {
    suspend fun getbystudent(studentid: Long) = dao.getbystudent(studentid)
    suspend fun insert(guardian: GuardianEntity) = dao.insert(guardian)
    suspend fun update(guardian: GuardianEntity) = dao.update(guardian)
}
