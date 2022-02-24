package com.tanaka.fastnews.views.details

import com.tanaka.fastnews.data.database.models.Article

sealed class DetailsEvents {
    data class SaveBookMark(val article: Article): DetailsEvents()
}