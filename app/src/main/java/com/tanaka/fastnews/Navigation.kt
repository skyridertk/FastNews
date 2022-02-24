package com.tanaka.fastnews

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.tanaka.fastnews.views.dashboard.MainScreen


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavigationComponent(toggle: (Boolean) -> Unit, bottomToggle: (Boolean) -> Unit) {
    val navController = rememberAnimatedNavController()

    AnimatedNavHost(navController = navController, startDestination = "main", route = "root") {
        composable(
            "main",
        ) {
            MainScreen(navController =navController, toggle=toggle)
        }
//        composable("bookmarks") {
//            BookmarksScreen(navController)
//        }
//
//        composable(
//            "news-detail/{article}"
//        ) {
//            NewsDetailScreen(navController)
//        }
//
//        composable(
//            "web/{url}",
//        ) {
//            val url =
//                it.arguments?.getString("url") ?: throw IllegalArgumentException("Url is null")
//            WebviewScreen(url, navController)
//        }
    }
}
