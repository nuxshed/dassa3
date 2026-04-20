package com.dass.ims.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.dass.ims.data.local.EnrollmentWithSubject
import com.dass.ims.data.local.entity.EnrollmentEntity

@Dao
interface EnrollmentDao {
    @Query(
        """
        SELECT e.id as enrollmentid, e.studentid, e.subjectid, su.name as subjectname, su.code as subjectcode,
               su.iselective, e.semester, e.grade
        FROM enrollments e
        JOIN subjects su ON e.subjectid = su.id
        WHERE e.studentid = :sid
    """
    )
    suspend fun getbystudent(sid: Long): List<EnrollmentWithSubject>

    @Insert
    suspend fun insertall(enrollments: List<EnrollmentEntity>)

    @Update
    suspend fun update(enrollment: EnrollmentEntity)
}
