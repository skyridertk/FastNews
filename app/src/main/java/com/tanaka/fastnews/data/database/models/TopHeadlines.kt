package com.tanaka.fastnews.data.database.models


data class TopHeadlines(
    val articles: List<Article>?,
    val status: String?,
    val totalResults: Int?
)