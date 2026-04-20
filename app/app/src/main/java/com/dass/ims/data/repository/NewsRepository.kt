package com.dass.ims.data.repository

import com.dass.ims.data.local.dao.NewsDao

class NewsRepository(private val dao: NewsDao) {
    suspend fun getall() = dao.getall()
}
