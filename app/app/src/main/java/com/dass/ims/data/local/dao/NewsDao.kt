package com.dass.ims.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.dass.ims.data.local.entity.NewsEntity

@Dao
interface NewsDao {
    @Query("SELECT * FROM news ORDER BY timestamp DESC")
    suspend fun getall(): List<NewsEntity>

    @Insert
    suspend fun insertall(news: List<NewsEntity>)
}
