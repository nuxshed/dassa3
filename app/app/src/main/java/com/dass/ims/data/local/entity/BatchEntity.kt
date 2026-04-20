package com.dass.ims.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "batches",
    foreignKeys = [
        ForeignKey(
            entity = CourseEntity::class,
            parentColumns = ["id"],
            childColumns = ["courseid"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("courseid")]
)
data class BatchEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val courseid: Long,
    val year: Int
)
