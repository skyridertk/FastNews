package com.tanaka.fastnews.views.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tanaka.fastnews.repository.NewsRepository
import com.tanaka.fastnews.repository.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {

    private val _state = MutableStateFlow(DashboardState())
    val state: StateFlow<DashboardState>
        get() = _state


    private val _sharedFlowState = MutableSharedFlow<UIEvent>()
    val sharedFlowState: SharedFlow<UIEvent>
        get() = _sharedFlowState


    fun onEvent(mainEvents: DashboardEvents){
        when(mainEvents){
            is DashboardEvents.ToggleUITheme -> {
                _state.value = state.value.copy(
                    darkMode = !state.value.darkMode
                )
            }
            is DashboardEvents.ViewTopHeadlines -> {
                viewModelScope.launch {
                    newsRepository.getTopHeadlines().collect {
                        when(it){
                            is Resource.Loading -> {
                                _state.value = state.value.copy(
                                    isLoading = true,
                                    isError = false
                                )
                            }

                            is Resource.Success -> {
                                _state.value = state.value.copy(
                                    isLoading = false,
                                    articles = it.data ?: emptyList()
                                )
                            }

                            is Resource.Error -> {
                                _state.value = state.value.copy(
                                    isLoading = true,
                                    isError = true
                                )

                                _sharedFlowState.emit(UIEvent.Error(it.message ?: "Error occurred"))
                            }
                            else -> {}
                        }
                    }
                }
            }
            is DashboardEvents.ViewHealth -> {
                viewModelScope.launch {
                    newsRepository.getTopHeadlines().collect {
                        when(it){
                            is Resource.Loading -> {
                                _state.value = state.value.copy(
                                    isLoading = true,
                                    isError = false
                                )
                            }

                            is Resource.Success -> {
                                _state.value = state.value.copy(
                                    isLoading = false,
                                    articles = it.data ?: emptyList()
                                )
                            }

                            is Resource.Error -> {
                                _state.value = state.value.copy(
                                    isLoading = true,
                                    isError = true
                                )

                                _sharedFlowState.emit(UIEvent.Error(it.message ?: "Error occurred"))
                            }
                            else -> {}
                        }
                    }
                }
            }
            is DashboardEvents.ViewSport -> {
                viewModelScope.launch {
                    newsRepository.getTopHeadlines().collect {
                        when(it){
                            is Resource.Loading -> {
                                _state.value = state.value.copy(
                                    isLoading = true,
                                    isError = false
                                )
                            }

                            is Resource.Success -> {
                                _state.value = state.value.copy(
                                    isLoading = false,
                                    articles = it.data ?: emptyList()
                                )
                            }

                            is Resource.Error -> {
                                _state.value = state.value.copy(
                                    isLoading = true,
                                    isError = true
                                )

                                _sharedFlowState.emit(UIEvent.Error(it.message ?: "Error occurred"))
                            }
                            else -> {}
                        }
                    }
                }
            }
            is DashboardEvents.ViewTechnology -> {
                viewModelScope.launch {
                    newsRepository.getTopHeadlines().collect {
                        when(it){
                            is Resource.Loading -> {
                                _state.value = state.value.copy(
                                    isLoading = true,
                                    isError = false
                                )
                            }

                            is Resource.Success -> {
                                _state.value = state.value.copy(
                                    isLoading = false,
                                    articles = it.data ?: emptyList()
                                )
                            }

                            is Resource.Error -> {
                                _state.value = state.value.copy(
                                    isLoading = true,
                                    isError = true
                                )

                                _sharedFlowState.emit(UIEvent.Error(it.message ?: "Error occurred"))
                            }
                            else -> {}
                        }
                    }
                }
            }
        }
    }




    companion object {
        const val TAG = "NewsViewModel"
    }

    sealed class UIEvent {
        data class Error(val message: String): UIEvent()
    }
}


