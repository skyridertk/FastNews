package com.tanaka.fastnews.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news")
data class News(
    val author: String?,
    val title: String?,
    val url: String?,
    val urlToImage: String?
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}

