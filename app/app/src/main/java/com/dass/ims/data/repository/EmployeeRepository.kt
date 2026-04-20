package com.dass.ims.data.repository

import com.dass.ims.data.local.dao.EmployeeDao

class EmployeeRepository(private val dao: EmployeeDao) {
    suspend fun getall() = dao.getall()
    suspend fun getbyid(id: Long) = dao.getbyid(id)
}
