package com.tanaka.fastnews.views.dashboard

import com.tanaka.fastnews.data.database.models.Article

data class DashboardState (
    val articles: List<Article> = emptyList(),
    val isError: Boolean = false,
    val darkMode: Boolean = false,
    val isLoading: Boolean = false
)