package com.tanaka.fastnews.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tanaka.fastnews.data.database.NewsDao
import com.tanaka.fastnews.data.database.entities.News

@Database(
    entities = [News::class],
    version = 1
)
abstract class NewsDatabase: RoomDatabase() {
    abstract fun newsDao(): NewsDao
}