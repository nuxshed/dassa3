package com.dass.ims.data.repository

import com.dass.ims.data.local.dao.CourseDao

class CourseRepository(private val dao: CourseDao) {
    suspend fun getall() = dao.getall()
    suspend fun getbyid(id: Long) = dao.getbyid(id)
}
