package com.dass.ims.data.repository

import com.dass.ims.data.local.dao.EnrollmentDao
import com.dass.ims.data.local.entity.EnrollmentEntity

class EnrollmentRepository(private val dao: EnrollmentDao) {
    suspend fun getbystudent(sid: Long) = dao.getbystudent(sid)
    suspend fun update(enrollment: EnrollmentEntity) = dao.update(enrollment)
}
