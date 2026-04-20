package com.dass.ims.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.dass.ims.data.local.entity.EmployeeEntity

@Dao
interface EmployeeDao {
    @Query("SELECT * FROM employees")
    suspend fun getall(): List<EmployeeEntity>

    @Query("SELECT * FROM employees WHERE id = :id")
    suspend fun getbyid(id: Long): EmployeeEntity?

    @Insert
    suspend fun insertall(employees: List<EmployeeEntity>)
}
