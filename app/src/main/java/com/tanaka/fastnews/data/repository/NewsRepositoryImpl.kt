package com.tanaka.fastnews.repository

import com.tanaka.fastnews.data.api.NewsAPI
import com.tanaka.fastnews.data.database.NewsDao
import com.tanaka.fastnews.data.database.entities.News
import com.tanaka.fastnews.data.database.models.Article
import com.tanaka.fastnews.data.database.models.TopHeadlines
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject




class NewsRepositoryImpl @Inject constructor(
    private val newsAPI: NewsAPI,
    private val newsDao: NewsDao
) : NewsRepository {
    override suspend fun getTopHeadlines(): Flow<Resource<List<Article>>> = flow {

        try {

            emit(Resource.Loading<List<Article>>())

            val req = newsAPI.getTopHeadlines(
                "bbc-news", "90abf4837e6744b09d8ac469e86e391d",
                language = "en", sortBy = "popularity"
            )

            if (req.isSuccessful) {
                emit(Resource.Success(data = (req.body() as TopHeadlines).articles as List<Article>))
            } else {

                throw Exception("Failed to fetch data")
            }

        } catch (ex: Exception) {

            emit(Resource.Error<List<Article>>(ex.localizedMessage ))

        }

    }

    override suspend fun getTechnology(): Flow<Resource<List<Article>>> = flow {
        try {
            emit(Resource.Loading<List<Article>>())

            val req = newsAPI.fetchTopHeadlines(
                "technology", "90abf4837e6744b09d8ac469e86e391d",
                language = "en", sortBy = "popularity"
            )

            if (req.isSuccessful) {
                val articles = (req.body() as TopHeadlines).articles ?: emptyList()
                emit(Resource.Success(articles))

            } else {
                throw Exception("Failed to get data")
            }


        } catch (ex: Exception) {

            emit(Resource.Error<List<Article>>(message = ex.localizedMessage ?: ""))
        }
    }

    override suspend fun deleteNewsItem(news: News) {
        newsDao.delete(news)
    }

    override suspend fun insertNewsItem(news: News) {
        newsDao.insert(news)
    }

    override suspend fun getNewsItems(): Flow<List<News>> {
        return newsDao.fetchNews()
    }

    override suspend fun deleteAll() {
        newsDao.deleteAll()
    }


    companion object {
        const val TAG = "NewsRepository"
    }
}

sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Loading<T>: Resource<T>()
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: String?, data: T? = null) : Resource<T>(data, message)
}