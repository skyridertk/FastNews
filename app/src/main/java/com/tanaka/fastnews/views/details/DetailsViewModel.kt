package com.tanaka.fastnews.views.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tanaka.fastnews.data.database.entities.News
import com.tanaka.fastnews.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val newsRepository: NewsRepository
): ViewModel(){

    fun onEvent(newsDetailEvents: DetailsEvents){
        when(newsDetailEvents){
            is DetailsEvents.SaveBookMark -> {
                viewModelScope.launch {
                    val news = News(
                        author = newsDetailEvents.article.author,
                        title = newsDetailEvents.article.title,
                        url = newsDetailEvents.article.url,
                        urlToImage = newsDetailEvents.article.urlToImage
                    )

                    newsRepository.insertNewsItem(news)
                }
            }
        }
    }
}