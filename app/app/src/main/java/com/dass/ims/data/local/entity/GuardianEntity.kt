package com.dass.ims.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "guardians",
    foreignKeys = [
        ForeignKey(
            entity = StudentEntity::class,
            parentColumns = ["id"],
            childColumns = ["studentid"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("studentid")]
)
data class GuardianEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val studentid: Long,
    val name: String,
    val relation: String,
    val phone: String,
    val isemergency: Boolean
)
