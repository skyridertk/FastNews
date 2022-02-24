package com.tanaka.fastnews.views

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.tanaka.fastnews.R
import com.tanaka.fastnews.data.database.models.Article
import com.tanaka.fastnews.views.details.DetailsEvents
import com.tanaka.fastnews.views.details.DetailsViewModel
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


@ExperimentalAnimationApi
@Preview
@Composable
fun NewsDetailScreen(
    navController: NavController = rememberAnimatedNavController(),
    article: Article? = null,
    newsDetailViewModel: DetailsViewModel = hiltViewModel()
) {
    val state = rememberScaffoldState()
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack()}) {
                        Icon(painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24), contentDescription = null)
                    }
                },
                actions = {
                    IconButton(onClick = {
                        newsDetailViewModel.onEvent(DetailsEvents.SaveBookMark(article!!))
                    }) {
                        Icon(painter = painterResource(id = R.drawable.ic_baseline_bookmark_24), contentDescription = null)
                    }
                }
            )
        }
    ) {
        Column(modifier = Modifier
            .padding(horizontal = 8.dp)
            .fillMaxSize()
            .verticalScroll(scrollState)) {
            Card(
                backgroundColor = Color(0XFF555555),
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .height(210.dp),
                elevation = 20.dp
            ) {
                Image(
                    painter = rememberImagePainter(
                        article?.urlToImage ?: "",
                    ),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                Row() {
                    Text("source:")
                    Text(article?.author ?: "?")
                }
                Text(article?.publishedAt ?: "__:__")
            }

            Text(article?.title ?: "No title yet",
                fontSize = 24.sp,
                modifier = Modifier.padding(vertical = 16.dp), fontWeight = FontWeight.SemiBold, lineHeight = 30.sp)

            Text(article?.content ?: "No content yet",modifier = Modifier.padding(vertical = 16.dp),
                fontSize = 16.sp, lineHeight = 30.sp, style = MaterialTheme.typography.body2)

            Button(onClick = {
                val url = article?.url?.replace("http://", "https://")
                val encodedUrl = URLEncoder.encode(url, StandardCharsets.UTF_8.toString())
                navController.navigate("web/$encodedUrl")
            }, modifier = Modifier.fillMaxWidth()) {
                Text("View More")
            }
        }
    }
}