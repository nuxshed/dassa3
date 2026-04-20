package com.dass.ims.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.dass.ims.data.local.entity.GuardianEntity

@Dao
interface GuardianDao {
    @Query("SELECT * FROM guardians WHERE studentid = :studentid")
    suspend fun getbystudent(studentid: Long): List<GuardianEntity>

    @Insert
    suspend fun insert(guardian: GuardianEntity): Long

    @Insert
    suspend fun insertall(guardians: List<GuardianEntity>)

    @Update
    suspend fun update(guardian: GuardianEntity)
}
