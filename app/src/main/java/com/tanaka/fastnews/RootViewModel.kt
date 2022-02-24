package com.tanaka.fastnews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tanaka.fastnews.data.datastore.UserPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(InternalCoroutinesApi::class)
@HiltViewModel
class RootViewModel @Inject constructor(userPreferences: UserPreferences) : ViewModel() {
    private val _state = MutableStateFlow(RootState())
    val state: StateFlow<RootState>
        get() = _state

    private val _sharedFlow = MutableSharedFlow<UIEvents>()
    val sharedFlow: SharedFlow<UIEvents>
        get() = _sharedFlow

    init {
        viewModelScope.launch {
            userPreferences.uiFlow.collect {
                _state.value = state.value.copy(
                    darkMode = !state.value.darkMode
                )
            }
        }
    }

    fun onEvent(rootEvents: RootEvents) {
        when (rootEvents) {
            RootEvents.ToggleTheme -> {
                _state.value = state.value.copy(
                    darkMode = !state.value.darkMode
                )

                viewModelScope.launch {
                    _sharedFlow.emit(UIEvents.UpdateTheme(state.value.darkMode))
                }
            }
            RootEvents.ToggleBottomAppBar -> {
                _state.value = state.value.copy(
                    isBottomBarVisible = !state.value.isBottomBarVisible
                )
            }
        }
    }
}


data class RootState(
    val darkMode: Boolean = false,
    val isBottomBarVisible: Boolean = true
)

sealed class RootEvents {
    object ToggleTheme : RootEvents()
    object ToggleBottomAppBar: RootEvents()
}

sealed class UIEvents {
    data class UpdateTheme(val value: Boolean): UIEvents()
}