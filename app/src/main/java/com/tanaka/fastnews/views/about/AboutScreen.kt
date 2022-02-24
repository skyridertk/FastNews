package com.tanaka.fastnews.views.about

import android.os.Build
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.tanaka.fastnews.BuildConfig
import com.tanaka.fastnews.R

@Preview
@Composable
fun AboutScreen() {
    val scaffoldState = rememberScaffoldState()
    Scaffold(scaffoldState = scaffoldState,

        topBar = {
            TopAppBar(title = {
                Text(text = "About")
            },
                navigationIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24),
                            contentDescription = null
                        )
                    }
                })
        }) {
        Column() {
            //Icon
            Box() {

            }
            Text("V${BuildConfig.VERSION_NAME}")
            Column() {
                Text("Developer")
                Text("github.com/skyridertk")
            }
            Column() {
                Text("API")
                Text("News API")
            }
        }
    }
}