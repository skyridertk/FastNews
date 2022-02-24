package com.tanaka.fastnews

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.tanaka.fastnews.ui.theme.FastNewsTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val rootViewModel by viewModels<RootViewModel>()

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val state by rootViewModel.state.collectAsState()
            val systemUiController = rememberSystemUiController()
            val scaffoldState = rememberScaffoldState()

            LaunchedEffect(Unit) {
                rootViewModel.sharedFlow.collect {
                    when (it) {
                        is UIEvents.UpdateTheme -> {

                            when (it.value) {
                                true -> {
                                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                                    systemUiController.setStatusBarColor(Color.Black)
                                }
                                false -> {
                                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                                    systemUiController.setStatusBarColor(Color.White)
                                }
                            }

                        }
                    }
                }

            }


            FastNewsTheme(darkTheme = state.darkMode) {
                Surface(color = MaterialTheme.colors.background) {
                    Scaffold(scaffoldState = scaffoldState, bottomBar = {
                        if (state.isBottomBarVisible) {
                            BottomNavigation {

                            }
                        }
                    }) {
                        NavigationComponent(toggle = {
                            rootViewModel.onEvent(RootEvents.ToggleTheme)
                        }, bottomToggle = {
                            rootViewModel.onEvent(RootEvents.ToggleBottomAppBar)
                        })
                    }
                }
            }
        }

    }
}






