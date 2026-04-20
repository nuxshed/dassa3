package com.dass.ims.data.repository

import com.dass.ims.data.local.dao.UserDao
import com.dass.ims.data.local.entity.UserEntity

class UserRepository(private val dao: UserDao) {
    suspend fun getbyid(id: Long) = dao.getbyid(id)
    suspend fun getall() = dao.getall()
    suspend fun insert(user: UserEntity) = dao.insert(user)
    suspend fun update(user: UserEntity) = dao.update(user)
}
