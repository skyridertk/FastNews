package com.tanaka.fastnews.views.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tanaka.fastnews.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val newsRepository: NewsRepository
): ViewModel() {
    private val _bookmarkState = MutableStateFlow<BookmarkUIState>(BookmarkUIState())
    val bookmarkState: MutableStateFlow<BookmarkUIState>
        get() = _bookmarkState

    init {
        getBookMarks()
    }

    fun onEvent(events: BookmarkEvents){
        when(events){

            is BookmarkEvents.DeleteNote -> {
                viewModelScope.launch {
                    newsRepository.deleteNewsItem(events.news)
                }
            }

            is BookmarkEvents.DeleteAll -> {
                viewModelScope.launch {
                    newsRepository.deleteAll()
                }
            }
        }


    }

    private fun getBookMarks(){
        newsRepository.getNewsItems()
            .onEach { listOfNews ->
                _bookmarkState.value = bookmarkState.value.copy(
                    bookmarks = listOfNews
                )
            }.launchIn(viewModelScope)
    }

    companion object {
        const val TAG = "Book"
    }
}