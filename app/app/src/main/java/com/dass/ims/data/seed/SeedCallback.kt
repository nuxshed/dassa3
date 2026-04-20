package com.dass.ims.data.seed

import android.content.ContentValues
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

class SeedCallback : RoomDatabase.Callback() {
    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        seed(db)
    }

    private fun seed(db: SupportSQLiteDatabase) {
        fun ins(table: String, block: ContentValues.() -> Unit): Long {
            val cv = ContentValues().apply(block)
            return db.insert(table, 1, cv)
        }

        val adminId = ins("users") {
            put("name", "Anita Sharma"); put("email", "anita@ims.edu")
            put("phone", "9000000001"); put("role", "admin"); put("isactive", true)
        }

        val aaravId = ins("users") {
            put("name", "Aarav Mehta"); put("email", "aarav@ims.edu")
            put("phone", "9000000002"); put("role", "student"); put("isactive", true)
        }
        val advikaId = ins("users") {
            put("name", "Advika Singh"); put("email", "advika@ims.edu")
            put("phone", "9000000003"); put("role", "student"); put("isactive", true)
        }
        val bhavyaId = ins("users") {
            put("name", "Bhavya Patel"); put("email", "bhavya@ims.edu")
            put("phone", "9000000004"); put("role", "student"); put("isactive", true)
        }
        val chiragId = ins("users") {
            put("name", "Chirag Gupta"); put("email", "chirag@ims.edu")
            put("phone", "9000000005"); put("role", "student"); put("isactive", true)
        }
        val diyaId = ins("users") {
            put("name", "Diya Nair"); put("email", "diya@ims.edu")
            put("phone", "9000000006"); put("role", "student"); put("isactive", true)
        }
        val eshanId = ins("users") {
            put("name", "Eshan Roy"); put("email", "eshan@ims.edu")
            put("phone", "9000000007"); put("role", "student"); put("isactive", true)
        }
        val farhanId = ins("users") {
            put("name", "Farhan Khan"); put("email", "farhan@ims.edu")
            put("phone", "9000000008"); put("role", "student"); put("isactive", true)
        }

        val priyaUserId = ins("users") {
            put("name", "Dr. Priya Rao"); put("email", "priya@ims.edu")
            put("phone", "9100000001"); put("role", "employee"); put("isactive", true)
        }
        val sureshUserId = ins("users") {
            put("name", "Dr. Suresh Kumar"); put("email", "suresh@ims.edu")
            put("phone", "9100000002"); put("role", "employee"); put("isactive", true)
        }
        val nehaUserId = ins("users") {
            put("name", "Dr. Neha Joshi"); put("email", "neha@ims.edu")
            put("phone", "9100000003"); put("role", "employee"); put("isactive", true)
        }
        val arjunUserId = ins("users") {
            put("name", "Dr. Arjun Das"); put("email", "arjun@ims.edu")
            put("phone", "9100000004"); put("role", "employee"); put("isactive", true)
        }
        val kavitaUserId = ins("users") {
            put("name", "Dr. Kavita Singh"); put("email", "kavita@ims.edu")
            put("phone", "9100000005"); put("role", "employee"); put("isactive", true)
        }
        val rajanUserId = ins("users") {
            put("name", "Dr. Rajan Tiwari"); put("email", "rajan@ims.edu")
            put("phone", "9100000006"); put("role", "employee"); put("isactive", true)
        }

        val csId = ins("courses") {
            put("name", "Computer Science"); put("code", "CS"); put("department", "Engineering")
        }
        val ecId = ins("courses") {
            put("name", "Electronics"); put("code", "EC"); put("department", "Engineering")
        }

        val cse2024Id = ins("batches") { put("name", "CSE-2024"); put("courseid", csId); put("year", 2024) }
        val ece2024Id = ins("batches") { put("name", "ECE-2024"); put("courseid", ecId); put("year", 2024) }
        val cse2023Id = ins("batches") { put("name", "CSE-2023"); put("courseid", csId); put("year", 2023) }
        ins("batches") { put("name", "CSD-2024"); put("courseid", csId); put("year", 2024) }
        val cseMsId = ins("batches") { put("name", "CSE-MS"); put("courseid", csId); put("year", 2024) }

        val priyaEmpId = ins("employees") { put("userid", priyaUserId); put("department", "CS"); put("designation", "Professor") }
        val sureshEmpId = ins("employees") { put("userid", sureshUserId); put("department", "CS"); put("designation", "Associate Professor") }
        val nehaEmpId = ins("employees") { put("userid", nehaUserId); put("department", "CS"); put("designation", "Assistant Professor") }
        ins("employees") { put("userid", arjunUserId); put("department", "EC"); put("designation", "Professor") }
        val kavitaEmpId = ins("employees") { put("userid", kavitaUserId); put("department", "CS"); put("designation", "Associate Professor") }
        val rajanEmpId = ins("employees") { put("userid", rajanUserId); put("department", "CS"); put("designation", "Assistant Professor") }

        val osId = ins("subjects") { put("name", "Operating Systems"); put("code", "OS"); put("courseid", csId); put("iselective", false); put("credits", 4) }
        val algId = ins("subjects") { put("name", "Algorithms"); put("code", "ALG"); put("courseid", csId); put("iselective", false); put("credits", 4) }
        val hciId = ins("subjects") { put("name", "Human-Computer Interaction"); put("code", "HCI"); put("courseid", csId); put("iselective", true); put("credits", 3) }
        val dbmsId = ins("subjects") { put("name", "Database Management Systems"); put("code", "DBMS"); put("courseid", csId); put("iselective", false); put("credits", 4) }
        val cnId = ins("subjects") { put("name", "Computer Networks"); put("code", "CN"); put("courseid", csId); put("iselective", false); put("credits", 4) }
        val seId = ins("subjects") { put("name", "Software Engineering"); put("code", "SE"); put("courseid", csId); put("iselective", false); put("credits", 3) }
        ins("subjects") { put("name", "Mathematics"); put("code", "MATH"); put("courseid", csId); put("iselective", false); put("credits", 4) }

        val aaravStudentId = ins("students") {
            put("userid", aaravId); put("roll", "CS2024001"); put("batchid", cse2024Id)
            put("courseid", csId); put("gpa", 8.5f); put("category", "UG"); put("status", "active")
            put("dob", "2003-05-12"); put("enrollmentdate", "2024-08-01"); put("gender", "Male")
        }
        ins("students") {
            put("userid", advikaId); put("roll", "CS2024002"); put("batchid", cse2024Id)
            put("courseid", csId); put("gpa", 9.1f); put("category", "UG"); put("status", "active")
            put("dob", "2003-08-22"); put("enrollmentdate", "2024-08-01"); put("gender", "Female")
        }
        ins("students") {
            put("userid", bhavyaId); put("roll", "CS2024003"); put("batchid", cse2024Id)
            put("courseid", csId); put("gpa", 7.8f); put("category", "UG"); put("status", "active")
            put("dob", "2003-11-05"); put("enrollmentdate", "2024-08-01"); put("gender", "Female")
        }
        ins("students") {
            put("userid", chiragId); put("roll", "EC2024001"); put("batchid", ece2024Id)
            put("courseid", ecId); put("gpa", 8.2f); put("category", "UG"); put("status", "active")
            put("dob", "2003-03-14"); put("enrollmentdate", "2024-08-01"); put("gender", "Male")
        }
        ins("students") {
            put("userid", diyaId); put("roll", "CS2024004"); put("batchid", cse2024Id)
            put("courseid", csId); put("gpa", 8.9f); put("category", "UG"); put("status", "active")
            put("dob", "2004-01-28"); put("enrollmentdate", "2024-08-01"); put("gender", "Female")
        }
        ins("students") {
            put("userid", eshanId); put("roll", "CS2023001"); put("batchid", cse2023Id)
            put("courseid", csId); put("gpa", 7.5f); put("category", "UG"); put("status", "active")
            put("dob", "2002-09-17"); put("enrollmentdate", "2023-08-01"); put("gender", "Male")
        }
        ins("students") {
            put("userid", farhanId); put("roll", "CSMS2024001"); put("batchid", cseMsId)
            put("courseid", csId); put("gpa", 8.7f); put("category", "PG"); put("status", "active")
            put("dob", "2000-12-03"); put("enrollmentdate", "2024-08-01"); put("gender", "Male")
        }

        ins("guardians") {
            put("studentid", aaravStudentId); put("name", "R. Sharma")
            put("relation", "Father"); put("phone", "9200000001"); put("isemergency", true)
        }

        val ttId = ins("timetables") {
            put("batchid", cse2024Id); put("name", "Sem 1 - 2024"); put("semester", 1); put("isactive", true)
        }

        fun slot(subjectid: Long, employeeid: Long, day: Int, period: Int, room: String) {
            ins("slots") {
                put("timetableid", ttId); put("subjectid", subjectid); put("employeeid", employeeid)
                put("day", day); put("period", period); put("room", room)
                put("type", "lecture"); put("isrecurring", true); put("batchname", "CSE-2024")
            }
        }

        slot(osId, priyaEmpId, 0, 0, "Room 101")
        slot(osId, priyaEmpId, 1, 0, "Room 101")
        slot(osId, priyaEmpId, 2, 0, "Room 101")
        slot(osId, priyaEmpId, 3, 0, "Room 101")

        slot(algId, sureshEmpId, 0, 1, "Room 102")
        slot(algId, sureshEmpId, 1, 1, "Room 102")
        slot(algId, sureshEmpId, 3, 1, "Room 102")

        slot(dbmsId, nehaEmpId, 0, 2, "Room 103")
        slot(dbmsId, nehaEmpId, 2, 2, "Room 103")
        slot(dbmsId, nehaEmpId, 4, 2, "Room 103")

        slot(hciId, kavitaEmpId, 1, 2, "Room 104")
        slot(hciId, kavitaEmpId, 3, 2, "Room 104")

        slot(cnId, rajanEmpId, 2, 1, "Room 105")
        slot(cnId, rajanEmpId, 4, 1, "Room 105")

        slot(seId, kavitaEmpId, 4, 0, "Room 104")
        slot(seId, kavitaEmpId, 2, 3, "Room 104")

        ins("enrollments") { put("studentid", aaravStudentId); put("subjectid", osId); put("semester", 1); put("grade", 78) }
        ins("enrollments") { put("studentid", aaravStudentId); put("subjectid", algId); put("semester", 1); put("grade", 92) }
        ins("enrollments") { put("studentid", aaravStudentId); put("subjectid", hciId); put("semester", 1); put("grade", 65) }
        ins("enrollments") { put("studentid", aaravStudentId); put("subjectid", dbmsId); put("semester", 1); put("grade", 84) }

        ins("news") {
            put("title", "Mid-Semester Examinations"); put("category", "Academic")
            put("timestamp", 1730000000000L)
            put("content", "Mid-semester exams will be held from Nov 4-8. Check the timetable.")
        }
        ins("news") {
            put("title", "Library Extended Hours"); put("category", "Announcement")
            put("timestamp", 1729500000000L)
            put("content", "Library will remain open till midnight during exam season.")
        }
        ins("news") {
            put("title", "Campus WiFi Upgrade"); put("category", "Infrastructure")
            put("timestamp", 1729000000000L)
            put("content", "Campus WiFi will be upgraded on Oct 25. Expect 2-hour downtime.")
        }
    }
}
