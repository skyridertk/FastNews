package com.tanaka.fastnews.repository

import com.tanaka.fastnews.data.database.entities.News
import com.tanaka.fastnews.data.database.models.Article
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    suspend fun getTopHeadlines(): Flow<Resource<List<Article>>>
    suspend fun getTechnology(): Flow<Resource<List<Article>>>
    suspend fun deleteNewsItem(news: News)
    suspend fun insertNewsItem(news: News)
    suspend fun getNewsItems(): Flow<List<News>>
    suspend fun deleteAll()
}