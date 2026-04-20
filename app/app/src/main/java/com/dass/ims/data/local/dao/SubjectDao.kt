package com.dass.ims.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.dass.ims.data.local.entity.SubjectEntity

@Dao
interface SubjectDao {
    @Query("SELECT * FROM subjects")
    suspend fun getall(): List<SubjectEntity>

    @Query("SELECT * FROM subjects WHERE id = :id")
    suspend fun getbyid(id: Long): SubjectEntity?

    @Query("SELECT * FROM subjects WHERE courseid = :courseid")
    suspend fun getbycourse(courseid: Long): List<SubjectEntity>

    @Insert
    suspend fun insertall(subjects: List<SubjectEntity>)
}
