package com.tanaka.fastnews.views.bookmark

import com.tanaka.fastnews.data.database.entities.News

sealed class BookmarkEvents {
    data class DeleteNote(val news: News): BookmarkEvents()
    object DeleteAll: BookmarkEvents()
}