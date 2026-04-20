package com.dass.ims.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "timetables",
    foreignKeys = [
        ForeignKey(
            entity = BatchEntity::class,
            parentColumns = ["id"],
            childColumns = ["batchid"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("batchid")]
)
data class TimetableEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val batchid: Long,
    val name: String,
    val semester: Int,
    val isactive: Boolean
)
