package com.dass.ims.data.repository

import com.dass.ims.data.local.dao.BatchDao

class BatchRepository(private val dao: BatchDao) {
    suspend fun getall() = dao.getall()
    suspend fun getbyid(id: Long) = dao.getbyid(id)
}
