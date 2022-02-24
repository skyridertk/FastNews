package com.tanaka.fastnews.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.tanaka.fastnews.data.database.entities.News
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {
    @Insert(onConflict = REPLACE)
    suspend fun insert(vararg news: News)

    @Delete
    suspend fun delete(news: News)

    @Query("delete from news")
    suspend fun deleteAll()

    @Query("select * from news")
    fun fetchNews(): Flow<List<News>>
}