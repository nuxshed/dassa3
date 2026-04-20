package com.dass.ims.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "students",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["id"],
            childColumns = ["userid"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = BatchEntity::class,
            parentColumns = ["id"],
            childColumns = ["batchid"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = CourseEntity::class,
            parentColumns = ["id"],
            childColumns = ["courseid"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("userid"), Index("batchid"), Index("courseid")]
)
data class StudentEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val userid: Long,
    val roll: String,
    val batchid: Long,
    val courseid: Long,
    val gpa: Float,
    val category: String,
    val status: String,
    val dob: String,
    val enrollmentdate: String,
    val gender: String
)
