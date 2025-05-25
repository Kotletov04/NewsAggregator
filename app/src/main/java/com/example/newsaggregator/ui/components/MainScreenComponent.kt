package com.example.newsaggregator.ui.components

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.webkit.WebView
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavArgument
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.toRoute
import coil3.compose.AsyncImage
import com.example.newsaggregator.R
import com.example.newsaggregator.domain.model.ItemModel
import com.example.newsaggregator.ui.theme.BACKGROUND_COLOR
import com.example.newsaggregator.ui.theme.SansationBold
import com.example.newsaggregator.ui.theme.SansationRegular
import kotlinx.serialization.Serializable

@Serializable
data class Guid(
    val guid: String
)


@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreenComponent(
    viewModel: MainScreenViewModel
) {
    val state = viewModel.state.value
    val navController = rememberNavController()

    if (state.isLoading) {
        Box(modifier = Modifier
            .fillMaxSize()
            .background(BACKGROUND_COLOR),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }

    }
    if (state.data != null) {
        NavHost(navController = navController, startDestination = "NEWS_SCREEN") {
            composable(route = "NEWS_SCREEN") { NewsLazyColumn(data = state.data!!, navController = navController, viewModel = viewModel) }
            composable<Guid> { NewsWebViews(guid = it.toRoute()) }
        }

    }

    val openDialog = remember { mutableStateOf(false) }
    if (state.error != "") {
        openDialog.value = true
    }
    if (openDialog.value) {
        BasicAlertDialog(
            modifier = Modifier
                .clip(RoundedCornerShape(15.dp))
                .background(Color.White)
                .height(100.dp)
                .width(200.dp),
            onDismissRequest = {openDialog.value = false}
        ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    textAlign = TextAlign.Center,
                    text = state.error!! + ":(",
                    fontFamily = SansationRegular,
                    fontSize = 14.sp
                )
            }

        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NewsLazyColumn(viewModel: MainScreenViewModel, data: List<ItemModel>, navController: NavController) {
    val isRefreshing = remember { mutableStateOf(false) }
    PullToRefreshBox(
        isRefreshing = isRefreshing.value,
        onRefresh = {
            isRefreshing.value = true
            viewModel.getNews()
            isRefreshing.value = false

        }
    ) {
        LazyColumn(
            modifier = Modifier
                .background(BACKGROUND_COLOR)
                .fillMaxSize()
        ) {
            items(data){ data -> NewsItem(data = data, navController = navController)}
        }
    }

}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NewsItem(data: ItemModel, navController: NavController) {
    val context = LocalContext.current

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Box(modifier = Modifier
            .clickable {
                navController.navigate(Guid(guid = data.guid))
            }
            .fillMaxWidth(0.9f)
            .height(400.dp)
            .clip(RoundedCornerShape(20.dp))

        ) {

            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                model = data.contents[1].url,
                contentDescription = null,
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(Color.Transparent, Color.White),
                            start = Offset(0f, -120f),
                            end = Offset(0f, 1000f)
                        )
                    )
                    .padding(15.dp),
                verticalArrangement = Arrangement.Bottom
            ) {
                Text(
                    text = data.title,
                    fontFamily = SansationBold,
                    fontSize = 23.sp
                )
                Text(
                    text = data.pubDate,
                    fontFamily = SansationRegular,
                    fontSize = 14.sp
                )
                Text(
                    text = data.dcCreator,
                    fontFamily = SansationRegular,
                    fontSize = 14.sp
                )
                Text(
                    modifier = Modifier.padding(top = 10.dp),
                    text = data.description.replace(Regex("<.*?>"), ""),
                    fontFamily = SansationRegular,
                    fontSize = 14.sp,
                    maxLines = 4,
                    overflow = TextOverflow.Ellipsis
                )
            }

        }

        val categories = data.categories

        Row(modifier = Modifier
            .fillMaxWidth(0.9f)
            .padding(5.dp),
            horizontalArrangement = Arrangement.Absolute.SpaceBetween) {
            FlowRow(
                modifier = Modifier.fillMaxWidth(0.8f)

            ) {
                categories.forEach { category ->
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
                        Text(text = category.value, color = Color.White, fontSize = 10.sp)
                    }

                }
            }

            Box(modifier = Modifier
                .padding(5.dp)
                .size(40.dp)
                .clickable {
                    val intent = Intent(Intent.ACTION_SEND).apply {
                        putExtra(Intent.EXTRA_TEXT, data.guid)
                        type = "text/plain"
                    }
                    val share = Intent.createChooser(intent, null)
                    context.startActivity(share)
                }
                .clip(shape = RoundedCornerShape(5.dp))
                .background(Color.White)) {
                Icon(modifier = Modifier.size(40.dp), painter = painterResource(R.drawable.baseline_share_24), contentDescription = null, )
            }
        }
    }




}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun NewsWebViews(guid: Guid) {
    AndroidView(
        factory = { context ->
            WebView(context).apply {
                this.webViewClient = webViewClient
            }

        },
        update = { webView ->
            webView.loadUrl(guid.guid)
        }
    )
}

