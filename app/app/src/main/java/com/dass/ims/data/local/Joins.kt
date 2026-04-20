package com.dass.ims.data.local

data class StudentWithUser(
    val studentid: Long,
    val userid: Long,
    val name: String,
    val email: String,
    val phone: String,
    val roll: String,
    val batchid: Long,
    val batchname: String,
    val courseid: Long,
    val gpa: Float,
    val category: String,
    val status: String,
    val dob: String,
    val enrollmentdate: String,
    val gender: String,
    val isactive: Boolean
)

data class SlotWithDetails(
    val slotid: Long,
    val timetableid: Long,
    val subjectid: Long,
    val subjectname: String,
    val subjectcode: String,
    val employeeid: Long,
    val employeename: String,
    val day: Int,
    val period: Int,
    val room: String,
    val type: String,
    val isrecurring: Boolean,
    val batchname: String
)

data class EnrollmentWithSubject(
    val enrollmentid: Long,
    val studentid: Long,
    val subjectid: Long,
    val subjectname: String,
    val subjectcode: String,
    val iselective: Boolean,
    val semester: Int,
    val grade: Int?
)
