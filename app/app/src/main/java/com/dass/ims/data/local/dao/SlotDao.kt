package com.dass.ims.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.dass.ims.data.local.SlotWithDetails
import com.dass.ims.data.local.entity.SlotEntity

@Dao
interface SlotDao {
    @Query(
        """
        SELECT sl.id as slotid, sl.timetableid, sl.subjectid, su.name as subjectname, su.code as subjectcode,
               sl.employeeid, u.name as employeename, sl.day, sl.period, sl.room, sl.type, sl.isrecurring, sl.batchname
        FROM slots sl
        JOIN subjects su ON sl.subjectid = su.id
        JOIN employees e ON sl.employeeid = e.id
        JOIN users u ON e.userid = u.id
        WHERE sl.timetableid = :ttid
    """
    )
    suspend fun getbytimetable(ttid: Long): List<SlotWithDetails>

    @Query("SELECT COUNT(*) FROM slots WHERE timetableid = :ttid AND subjectid = :subid")
    suspend fun countsubjectslots(ttid: Long, subid: Long): Int

    @Query("SELECT COUNT(*) FROM slots WHERE timetableid = :ttid AND employeeid = :empid")
    suspend fun countemployeeslots(ttid: Long, empid: Long): Int

    @Insert
    suspend fun insert(slot: SlotEntity): Long

    @Insert
    suspend fun insertall(slots: List<SlotEntity>)

    @Update
    suspend fun update(slot: SlotEntity)

    @Delete
    suspend fun delete(slot: SlotEntity)
}
