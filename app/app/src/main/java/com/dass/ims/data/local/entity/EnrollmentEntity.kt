package com.dass.ims.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "enrollments",
    foreignKeys = [
        ForeignKey(
            entity = StudentEntity::class,
            parentColumns = ["id"],
            childColumns = ["studentid"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = SubjectEntity::class,
            parentColumns = ["id"],
            childColumns = ["subjectid"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("studentid"), Index("subjectid")]
)
data class EnrollmentEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val studentid: Long,
    val subjectid: Long,
    val semester: Int,
    val grade: Int?
)
