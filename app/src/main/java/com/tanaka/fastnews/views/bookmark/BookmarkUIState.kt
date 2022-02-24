package com.tanaka.fastnews.views.bookmark

import com.tanaka.fastnews.data.database.entities.News

data class BookmarkUIState(
    val bookmarks: List<News> = emptyList()
)
