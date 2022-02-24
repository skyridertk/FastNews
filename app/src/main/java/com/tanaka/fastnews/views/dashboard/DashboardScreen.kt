package com.tanaka.fastnews.views.dashboard

import android.net.Uri
import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.gson.Gson
import com.tanaka.fastnews.R
import com.tanaka.fastnews.data.database.models.Article
import com.tanaka.fastnews.ui.theme.FastNewsTheme
import kotlinx.coroutines.flow.collect


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainScreen(
    navController: NavController = rememberAnimatedNavController(),
    dashboardViewModel: DashboardViewModel = hiltViewModel(),
    toggle: (Boolean) -> Unit
) {

    val newsData by dashboardViewModel.state.collectAsState()

    Log.d("NewsListState", "NewsListState: ${newsData.toString()}")
    Log.d("NewsListState Bool", "NewsListState: ${newsData.darkMode}")
    val context = LocalContext.current
    
    LaunchedEffect(Unit){
        dashboardViewModel.sharedFlowState.collect {
            when(it){
                is DashboardViewModel.UIEvent.Error -> {
//                    Snackbar.make(context, "", Snack).sh
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Fast News", style = MaterialTheme.typography.h2, color = Color.Black)
                },
                actions = {
                    IconButton(onClick = {
                        dashboardViewModel.onEvent(DashboardEvents.ToggleUITheme)
                        toggle(true)
                    }) {
                        Icon(
                            painter = painterResource(id = if (!newsData.darkMode) R.drawable.ic_baseline_light_mode_24 else R.drawable.ic_dark_mode_6682),
                            contentDescription = null
                        )
                    }
                },
                backgroundColor = Color.DarkGray
            )
        }

    ) {
        NewsInformation(newsData.articles, navController, dashboardViewModel)
    }
}


@Composable
fun NewsInformation(
    listOfArticles: List<Article>,
    navController: NavController,
    dashboardViewModel: DashboardViewModel
) {
    Column {
        CategorySwitcher(dashboardViewModel)
        NewsList(listOfArticles, navController)
    }
}


@ExperimentalAnimationApi
@Preview
@Composable
fun NewsCardListPreview() {

    val article = Article(
        "Jason Green",
        "Something",
        "Welco",
        "12-12-12",
        "" +
                "Something awesome happened on the way to the launch and the plane blew up",
        "www.google.com",
        "Text"
    )
    FastNewsTheme {
        Surface(color = MaterialTheme.colors.background) {
            NewsList(listOf(article), rememberAnimatedNavController())
        }
    }
}

@Composable
fun NewsList(listOfArticles: List<Article>, navController: NavController) {

    LazyColumn {
        items(listOfArticles) { item ->
            NewsCard(item, { article ->
                val json = Uri.encode(Gson().toJson(article))
                navController.navigate("news-detail/$json")
            }, modifier = Modifier.padding(horizontal = 4.dp))
        }
    }
}


@Composable
fun CategorySwitcher(dashboardViewModel: DashboardViewModel) {
    val scrollState = rememberScrollState()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(scrollState)
    ) {
        CustomButton(title = "All", color = Color.White, onClickItem = {
            dashboardViewModel.onEvent(DashboardEvents.ViewAll)
        })
        CustomButton(title = "Technology", color = Color.White, onClickItem = {
            dashboardViewModel.onEvent(DashboardEvents.ViewTechnology)
        })
        CustomButton(title = "Sports", color = Color.White, onClickItem = {
            dashboardViewModel.onEvent(DashboardEvents.ViewSport)
        })
        CustomButton(title = "Health", color = Color.White, onClickItem = {
            dashboardViewModel.onEvent(DashboardEvents.ViewHealth)
        })
    }
}

@Composable
fun CustomButton(title: String, color: Color, onClickItem: (String) -> Unit) {
    Box(modifier = Modifier.padding(horizontal = 8.dp, vertical = 16.dp)) {
        Button(
            onClick = { onClickItem(title) },
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF796DB8))
        ) {
            Text(title, color = color, style = MaterialTheme.typography.body2)
        }
    }
}


@Composable
private fun NewsCard(
    article: Article,
    onClickItem: (Article) -> Unit,
    modifier: Modifier
) {
    val context = LocalContext.current
    Column(modifier = modifier
        .padding(vertical = 32.dp)
        .clickable { onClickItem(article) }) {
        Card(
            backgroundColor = Color(0XFF555555),
            modifier = modifier
                .clip(RoundedCornerShape(12.dp))
                .height(210.dp),
            elevation = 20.dp
        ) {
            Image(
                painter = rememberImagePainter(
                    article.urlToImage
                ),
                contentDescription = null,
                modifier = modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )

        }

        Text(
            text = article.title ?: "No text yet",
            fontWeight = FontWeight.Bold,
            modifier = modifier.padding(vertical = 8.dp),
            style = MaterialTheme.typography.body2
        )
    }

}
