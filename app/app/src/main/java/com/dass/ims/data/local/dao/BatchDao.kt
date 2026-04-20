package com.dass.ims.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.dass.ims.data.local.entity.BatchEntity

@Dao
interface BatchDao {
    @Query("SELECT * FROM batches")
    suspend fun getall(): List<BatchEntity>

    @Query("SELECT * FROM batches WHERE id = :id")
    suspend fun getbyid(id: Long): BatchEntity?

    @Insert
    suspend fun insertall(batches: List<BatchEntity>)
}
