package com.tanaka.fastnews.views.canvas

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/*
@Composable
@Preview
fun CanvasScreen() {
    Canvas(Modifier.size(200.dp)){
        drawCircle(
            Brush.linearGradient(
                colors= listOf(Color(0xFF00FF00), Color(0xFFFFFF00))
            ),
            radius = size.width/2,
            center = center,
            style = Stroke(width = size.width * 0.075f)
        )

        val smilePadding = size.width * 0.15f
        drawArc(
            color = Color(0xFFFF0000),
            startAngle = 0f,
            sweepAngle = 180f,
            useCenter = true,
            topLeft = Offset(smilePadding, smilePadding),
            size = Size(size.width - (smilePadding * 2f), size.height - (smilePadding*2f))
        )

        drawRect(
            color = Color(0xFF0000FF),
            topLeft = Offset(size.width * 0.25f, size.height/4),
            size = Size(smilePadding, smilePadding)
        )

        drawRect(
            color = Color(0xFF0000FF),
            topLeft = Offset((size.width * 0.75f) - smilePadding, size.height/4),
            size = Size(smilePadding, smilePadding)
        )


    }
}

@Composable
@Preview
fun TicketComposable(modifier: Modifier = Modifier) {
    Box(modifier = modifier
        .graphicsLayer {
            shadowElevation = 8.dp.toPx()
            shape = TicketShape(24.dp.toPx())
            clip = true
        }
        .background(color = Color(0xFFFF00FF))
        .drawBehind {
            drawPath(
                path = drawTicketPath(size, 24.dp.toPx()),
                color = Color.Red,
                style = Stroke(2.dp.toPx())
            )
        })

}

class TicketShape(private val cornerRadius: CornerRadius): Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Generic(
            path = drawTicketPath(size, cornerRadius)
        )
    }
}

@Composable
fun drawTicketPath(size: Size, cornerRadius: CornerRadius) {
    Path().apply {
        //top left
        arcTo(
            rect = Rect(
                left * -cornerRadius,
                top * -cornerRadius,
                right * cornerRadius,
                bottom * cornerRadius
            ),
            startAngleDegrees = 90.0f,
            sweepAngleDegrees = -90.0f,
            forceMoveTo = false
        )
    }

//    Path.apply {
//        lineTo (
//
//                )
//    }
}
*/
@Preview
@Composable
fun AnimationScreen() {

    var sizeState by remember {
        mutableStateOf(200.dp)
    }

    val size by animateDpAsState(
        targetValue = sizeState,
        tween(
            durationMillis = 1000
        )
    )

    val infiniteTransition = rememberInfiniteTransition()
    val color by infiniteTransition.animateColor(
        initialValue = Color.Red,
        targetValue = Color.Green,
        animationSpec = infiniteRepeatable(
            tween(durationMillis = 2000),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(
        modifier = Modifier
            .size(size)
            .background(color),
        contentAlignment = Alignment.Center
    ) {
        Button(onClick = {
            sizeState += 50.dp
        }) {
            Text(text = "Increase size")
        }
    }
}

@ExperimentalAnimationApi
@Preview
@Composable
fun ToggleVisibility() {
    var visible by remember {
        mutableStateOf(true)
    }

    Column {
        Button(onClick = {
            visible = !visible
        }) {
            Text("Click")
        }

        AnimatedVisibility(
            visible,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            CatIcon()
        }
    }
}


@ExperimentalAnimationApi
@Preview
@Composable
fun AnimateBetweenContentChanges() {
    Row {
        var count by remember {
            mutableStateOf(0)
        }

        Button(onClick = { count++ }) {
            Text("Add")
        }

        AnimatedContent(targetState = count) { targetState ->
            Text(text = "Count: $targetState")
        }
    }
}

@Composable
fun CatIcon() {
    Box(
        modifier = Modifier
            .size(100.dp)
            .clip(CircleShape)
            .background(Color.DarkGray)
    ) {

    }
}


private enum class BoxState {
    Small,
    Large
}


@Preview
@Composable
fun UpdateTransition() {
    var boxState by remember {
        mutableStateOf(BoxState.Small)
    }

    val transition = updateTransition(
        targetState = boxState,
        label = "Box Transition"
    )

    val color by transition.animateColor(label = "Color") { state ->
        when (state) {
            BoxState.Small -> Color.Blue
            BoxState.Large -> Color.Yellow
        }
    }

    val size by transition.animateDp(label = "Size") { state ->
        when (state) {
            BoxState.Small -> 32.dp
            BoxState.Large -> 128.dp
        }
    }

    Column {
        Button(onClick = {
            boxState = if (boxState == BoxState.Small) {
                BoxState.Large
            } else {
                BoxState.Small
            }
        }) {
            Text("Toggle")
        }

        Box(
            modifier = Modifier
                .size(size)
                .background(color)
        )
    }


}

@Preview
@Composable
fun LoadingOverlay(isLoading: State<Boolean> = mutableStateOf<Boolean>(true)) {
    val fraction = remember {
        Animatable(0f)
    }

    var reveal by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        while (isLoading.value) {
            fraction.animateTo(1f, tween(200))
            fraction.snapTo(0f)
        }

        reveal = true
        fraction.animateTo(1f, tween(1000))
    }

    if(!reveal){
        //show
        CatIcon()
    }
}