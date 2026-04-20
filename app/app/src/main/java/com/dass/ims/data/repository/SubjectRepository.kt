package com.dass.ims.data.repository

import com.dass.ims.data.local.dao.SubjectDao

class SubjectRepository(private val dao: SubjectDao) {
    suspend fun getall() = dao.getall()
    suspend fun getbyid(id: Long) = dao.getbyid(id)
    suspend fun getbycourse(courseid: Long) = dao.getbycourse(courseid)
}
