package com.dass.ims.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "slots",
    foreignKeys = [
        ForeignKey(
            entity = TimetableEntity::class,
            parentColumns = ["id"],
            childColumns = ["timetableid"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = SubjectEntity::class,
            parentColumns = ["id"],
            childColumns = ["subjectid"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = EmployeeEntity::class,
            parentColumns = ["id"],
            childColumns = ["employeeid"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("timetableid"), Index("subjectid"), Index("employeeid")]
)
data class SlotEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val timetableid: Long,
    val subjectid: Long,
    val employeeid: Long,
    val day: Int,
    val period: Int,
    val room: String,
    val type: String,
    val isrecurring: Boolean,
    val batchname: String
)
