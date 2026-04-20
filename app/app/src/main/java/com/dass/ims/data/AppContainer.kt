package com.dass.ims.data

import android.content.Context
import com.dass.ims.data.local.ImsDatabase
import com.dass.ims.data.repository.*

class AppContainer(context: Context) {
    private val db = ImsDatabase.get(context)
    val users = UserRepository(db.userDao())
    val students = StudentRepository(db.studentDao())
    val employees = EmployeeRepository(db.employeeDao())
    val guardians = GuardianRepository(db.guardianDao())
    val courses = CourseRepository(db.courseDao())
    val subjects = SubjectRepository(db.subjectDao())
    val batches = BatchRepository(db.batchDao())
    val timetables = TimetableRepository(db.timetableDao())
    val slots = SlotRepository(db.slotDao())
    val enrollments = EnrollmentRepository(db.enrollmentDao())
    val news = NewsRepository(db.newsDao())
}
