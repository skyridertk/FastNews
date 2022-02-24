package com.tanaka.fastnews.views.bookmark

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.tanaka.fastnews.R
import com.tanaka.fastnews.data.database.entities.News
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Preview
@Composable
fun BookmarksScreen(navController: NavController = rememberNavController(), bookmarkViewModel: BookmarkViewModel = hiltViewModel()) {
    val scaffoldState = rememberScaffoldState()
    var expandedState by remember {
        mutableStateOf<Boolean>(false)
    }
    Scaffold(scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(title = {
                Text("Bookmarks")
            }, Modifier.background(Color.DarkGray),

                actions = {
                    IconButton(onClick = { expandedState = true }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_more_vert_24),
                            contentDescription = null
                        )

                        DropdownMenu(
                            expanded = expandedState,
                            onDismissRequest = { expandedState = false }) {
                            DropdownMenuItem(onClick = {
                                bookmarkViewModel.onEvent(BookmarkEvents.DeleteAll)
                                expandedState = false
                            }) {
                                Text("Clear all Bookmarks")
                            }
                        }
                    }
                }

            )
        }) {
        BookmarkState(bookmarkViewModel, navController)
    }
}

@Composable
fun BookmarkState(bookmarkViewModel: BookmarkViewModel, navController: NavController) {
    val bookmarksData by bookmarkViewModel.bookmarkState.collectAsState()



    BookmarksList(bookmarksData.bookmarks, onClickItem = {
        val url = it.replace("http://", "https://")
        val encodedUrl = URLEncoder.encode(url, StandardCharsets.UTF_8.toString())
        navController.navigate("web/$encodedUrl")
    }) {
        bookmarkViewModel.onEvent(BookmarkEvents.DeleteNote(it))
    }
}

@Composable
fun BookmarksList(list: List<News>, onClickItem: (String) -> Unit, removeItem: (News) -> Unit) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(32.dp),
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp)
    ) {
        items(list){ item ->
            Bookmark(item, onClickItem, removeItem)
        }
    }
}

@Composable
fun Bookmark(news: News, onClickItem: (String)->Unit, removeItem: (News) -> Unit) {
    var openDialog by remember {
        mutableStateOf<Boolean>(false)
    }

    Card(modifier = Modifier.pointerInput(Unit) {
        detectTapGestures(
            onLongPress = {
                openDialog = true
            },
            onTap = {
                onClickItem(news.url?: "")
            }
        )
    }) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(Color.Gray)
                .padding(horizontal = 16.dp, vertical = 16.dp)
                .clip(RoundedCornerShape(15.dp))
                .fillMaxWidth(1f)
        ) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .background(Color.Yellow)
            ) {
//                    Image(painter = painterResource(), contentDescription = null)
            }
            Spacer(modifier = Modifier.size(12.dp))
            Text(
                news.title ?: "No news yet",
                style = MaterialTheme.typography.body2,
                modifier = Modifier.fillMaxWidth(1f)
            )
        }
    }

    if (openDialog) {
        AlertDialog(
            onDismissRequest = { openDialog = false },
            title = {
                Text("Remove News Item")
            },
            text = {
                Text("Lets remove some items")
            },
            confirmButton = {
                Button(onClick = {
                    openDialog = false
                    removeItem(news)
                }) {
                    Text("Yes")
                }
            },
            dismissButton = {
                Button(onClick = { openDialog = false }) {
                    Text(text = "Dismiss")
                }
            }
        )
    }
}