package com.dass.ims.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.dass.ims.data.local.entity.CourseEntity

@Dao
interface CourseDao {
    @Query("SELECT * FROM courses")
    suspend fun getall(): List<CourseEntity>

    @Query("SELECT * FROM courses WHERE id = :id")
    suspend fun getbyid(id: Long): CourseEntity?

    @Insert
    suspend fun insertall(courses: List<CourseEntity>)
}
