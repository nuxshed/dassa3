package com.dass.ims.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dass.ims.data.local.dao.*
import com.dass.ims.data.local.entity.*
import com.dass.ims.data.seed.SeedCallback

@Database(
    entities = [
        UserEntity::class,
        StudentEntity::class,
        EmployeeEntity::class,
        GuardianEntity::class,
        CourseEntity::class,
        SubjectEntity::class,
        BatchEntity::class,
        TimetableEntity::class,
        SlotEntity::class,
        EnrollmentEntity::class,
        NewsEntity::class,
    ],
    version = 1,
    exportSchema = false
)
abstract class ImsDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun studentDao(): StudentDao
    abstract fun employeeDao(): EmployeeDao
    abstract fun guardianDao(): GuardianDao
    abstract fun courseDao(): CourseDao
    abstract fun subjectDao(): SubjectDao
    abstract fun batchDao(): BatchDao
    abstract fun timetableDao(): TimetableDao
    abstract fun slotDao(): SlotDao
    abstract fun enrollmentDao(): EnrollmentDao
    abstract fun newsDao(): NewsDao

    companion object {
        @Volatile private var instance: ImsDatabase? = null
        fun get(context: Context): ImsDatabase = instance ?: synchronized(this) {
            instance ?: Room.databaseBuilder(context.applicationContext, ImsDatabase::class.java, "ims.db")
                .addCallback(SeedCallback())
                .build()
                .also { instance = it }
        }
    }
}
