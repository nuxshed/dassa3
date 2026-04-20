package com.dass.ims.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.dass.ims.data.local.StudentWithUser
import com.dass.ims.data.local.entity.StudentEntity

@Dao
interface StudentDao {
    @Query(
        """
        SELECT s.id as studentid, s.userid, u.name, u.email, u.phone, s.roll,
               s.batchid, b.name as batchname, s.courseid, s.gpa, s.category,
               s.status, s.dob, s.enrollmentdate, s.gender, u.isactive
        FROM students s
        JOIN users u ON s.userid = u.id
        JOIN batches b ON s.batchid = b.id
    """
    )
    suspend fun getall(): List<StudentWithUser>

    @Query(
        """
        SELECT s.id as studentid, s.userid, u.name, u.email, u.phone, s.roll,
               s.batchid, b.name as batchname, s.courseid, s.gpa, s.category,
               s.status, s.dob, s.enrollmentdate, s.gender, u.isactive
        FROM students s
        JOIN users u ON s.userid = u.id
        JOIN batches b ON s.batchid = b.id
        WHERE s.id = :id
    """
    )
    suspend fun getbyid(id: Long): StudentWithUser?

    @Query(
        """
        SELECT s.id as studentid, s.userid, u.name, u.email, u.phone, s.roll,
               s.batchid, b.name as batchname, s.courseid, s.gpa, s.category,
               s.status, s.dob, s.enrollmentdate, s.gender, u.isactive
        FROM students s
        JOIN users u ON s.userid = u.id
        JOIN batches b ON s.batchid = b.id
        WHERE u.name LIKE '%' || :q || '%' OR s.roll LIKE '%' || :q || '%'
    """
    )
    suspend fun search(q: String): List<StudentWithUser>

    @Query(
        """
        SELECT s.id as studentid, s.userid, u.name, u.email, u.phone, s.roll,
               s.batchid, b.name as batchname, s.courseid, s.gpa, s.category,
               s.status, s.dob, s.enrollmentdate, s.gender, u.isactive
        FROM students s
        JOIN users u ON s.userid = u.id
        JOIN batches b ON s.batchid = b.id
        WHERE (:batchid IS NULL OR s.batchid = :batchid)
          AND (:gender IS NULL OR s.gender = :gender)
          AND (:category IS NULL OR s.category = :category)
          AND s.gpa >= :mingpa AND s.gpa <= :maxgpa
    """
    )
    suspend fun filter(
        batchid: Long?,
        gender: String?,
        category: String?,
        mingpa: Float,
        maxgpa: Float
    ): List<StudentWithUser>

    @Insert
    suspend fun insert(student: StudentEntity): Long

    @Update
    suspend fun update(student: StudentEntity)
}
