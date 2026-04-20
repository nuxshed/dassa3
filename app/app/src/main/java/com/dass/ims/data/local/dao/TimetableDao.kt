package com.dass.ims.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.dass.ims.data.local.entity.TimetableEntity

@Dao
interface TimetableDao {
    @Query("SELECT * FROM timetables WHERE isactive = 1 LIMIT 1")
    suspend fun getactive(): TimetableEntity?

    @Query("SELECT * FROM timetables")
    suspend fun getall(): List<TimetableEntity>

    @Query("SELECT * FROM timetables WHERE id = :id")
    suspend fun getbyid(id: Long): TimetableEntity?

    @Insert
    suspend fun insert(timetable: TimetableEntity): Long

    @Update
    suspend fun update(timetable: TimetableEntity)

    @Insert
    suspend fun insertall(timetables: List<TimetableEntity>)
}
