package com.dass.ims.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.dass.ims.data.local.entity.UserEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM users WHERE id = :id")
    suspend fun getbyid(id: Long): UserEntity?

    @Query("SELECT * FROM users")
    suspend fun getall(): List<UserEntity>

    @Insert
    suspend fun insert(user: UserEntity): Long

    @Insert
    suspend fun insertall(users: List<UserEntity>)

    @Update
    suspend fun update(user: UserEntity)
}
