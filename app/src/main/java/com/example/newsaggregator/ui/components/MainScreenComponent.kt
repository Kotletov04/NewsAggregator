package com.example.newsaggregator.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.newsaggregator.R
import com.example.newsaggregator.domain.model.NewsModel
import com.example.newsaggregator.ui.theme.BACKGROUND_COLOR
import com.example.newsaggregator.ui.theme.SansationBold
import com.example.newsaggregator.ui.theme.SansationRegular


@Composable
fun MainScreenComponent(
    viewModel: MainScreenViewModel
) {

    val state = viewModel.state.value

    if (state.isLoading) {
        CircularProgressIndicator()
    }
    if (state.data != null) {
        NewsLazyColumn(data = state.data!!)
    }
    /*Box(modifier = Modifier.background(BACKGROUND_COLOR).fillMaxSize(),
        contentAlignment = Alignment.TopCenter) {
        NewsItem()
    }*/


}

@Composable
private fun NewsLazyColumn(data: NewsModel) {
    LazyColumn(
        modifier = Modifier.background(BACKGROUND_COLOR).fillMaxSize()
    ) {
        items(5){NewsItem()}
    }

    }



@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NewsItem() {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Box(modifier = Modifier.fillMaxWidth(0.9f).height(400.dp).clip(RoundedCornerShape(20.dp))) {

            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                model = "https://i.guim.co.uk/img/media/2543ef2dcdc901ffe43358c1817d44c15f105c59/3_0_4947_3957/master/4947.jpg?width=460&quality=85&auto=format&fit=max&s=a3572480248751bf286cb77cfcf3b27a",
                contentDescription = null,
            )

            Column(
                modifier = Modifier
                    .fillMaxSize().background(
                        brush = Brush.linearGradient(
                            colors = listOf(Color.Transparent, Color.White),
                            start = Offset(0f, -200f),
                            end = Offset(0f, 1000f)
                        )
                    )
                    .padding(15.dp),
                verticalArrangement = Arrangement.Bottom
            ) {
                Text(
                    text = "I’m taking beta blockers for my anxiety – and so are many of my friends. Is that a problem?",
                    fontFamily = SansationBold,
                    fontSize = 23.sp
                )
                Text(
                    text = "Tue, 20 May 2025 09:00:04 GMT",
                    fontFamily = SansationRegular,
                    fontSize = 14.sp
                )
                Text(
                    text = "Polly Hudson",
                    fontFamily = SansationRegular,
                    fontSize = 14.sp
                )
                Text(
                    text = "Dreading the thought of giving a speech, or stressed about a big work event? Your GP may prescribe beta blockers to reduce the effects of adrenaline on your heart. Here’s what happened when I took them",
                    fontFamily = SansationRegular,
                    fontSize = 14.sp
                )
            }

        }

        val rects = listOf("#tag", "#tag", "#tag", "#tag", "#tag", "#tag", "#tag", "#tag")

        Row(modifier = Modifier.fillMaxWidth(0.9f).padding(5.dp),
            horizontalArrangement = Arrangement.Absolute.SpaceBetween) {
            FlowRow(

            ) {
                rects.forEach { rect ->
                    Box(
                        modifier = Modifier
                            .padding(3.dp)
                            .border(
                                width = 1.dp,
                                color = Color.White,
                                shape = RoundedCornerShape(10.dp)
                            )
                            .padding(3.dp)
                    ) {
                        Text(text = rect, color = Color.White, fontSize = 10.sp)
                    }

                }
            }
            Box(modifier = Modifier.padding(5.dp).size(40.dp).clickable {  }.clip(shape = RoundedCornerShape(5.dp)).background(Color.White)) {
                Icon(modifier = Modifier.size(40.dp), painter = painterResource(R.drawable.baseline_share_24), contentDescription = null, )
            }
        }
    }




}


