package com.dass.ims.data.repository

import com.dass.ims.data.local.dao.StudentDao
import com.dass.ims.data.local.entity.StudentEntity

class StudentRepository(private val dao: StudentDao) {
    suspend fun getall() = dao.getall()
    suspend fun getbyid(id: Long) = dao.getbyid(id)
    suspend fun search(q: String) = dao.search(q)
    suspend fun filter(
        batchid: Long?,
        gender: String?,
        category: String?,
        mingpa: Float,
        maxgpa: Float
    ) = dao.filter(batchid, gender, category, mingpa, maxgpa)
    suspend fun insert(student: StudentEntity) = dao.insert(student)
    suspend fun update(student: StudentEntity) = dao.update(student)
}
