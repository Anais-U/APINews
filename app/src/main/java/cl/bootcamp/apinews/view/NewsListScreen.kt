package cl.bootcamp.apinews.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import cl.bootcamp.apinews.state.NewsState
import cl.bootcamp.apinews.viewModel.NewsViewModel

@Composable
fun NewsListScreen(
    navController: NavHostController,
    viewModel: NewsViewModel,
    country: String,
    category: String,
    page: Int,
    state: NewsState
) {
    LaunchedEffect(Unit) {
        viewModel.fetchAllNews(country, category, page)
    }

    val newsList = state.newsList

    Box(modifier = Modifier.fillMaxSize()) {
        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else if (state.error != null) {
            Text(text = state.error, color = Color.Red, modifier = Modifier.align(Alignment.Center))
        } else {
            LazyColumn {
                items(newsList) { article ->
                    NewsItem(article) {
                        navController.navigate("detailsNews/${article.url}")
                    }
                }

                item {
                    if (newsList.isNotEmpty()) {
                        Button(
                            onClick = {
                                // Implementar lógica para cargar más artículos
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Text("Cargar más noticias")
                        }
                    }
                }
            }
        }
    }
}