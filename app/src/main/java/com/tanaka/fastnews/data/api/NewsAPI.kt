package com.tanaka.fastnews.data.api

import com.tanaka.fastnews.data.database.models.TopHeadlines
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPI {
    @GET("top-headlines/")
    suspend fun getTopHeadlines(
        @Query("sources") sources: String,
        @Query("apiKey") apiKey: String,
        @Query("language") language: String,
        @Query("sortBy") sortBy: String
    ): Response<TopHeadlines>

    @GET("top-headlines/")
    suspend fun fetchTopHeadlines(
        @Query("category") category: String,
        @Query("apiKey") apiKey: String,
        @Query("language") language: String,
        @Query("sortBy") sortBy: String
    ): Response<TopHeadlines>
}