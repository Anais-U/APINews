package cl.bootcamp.apinews.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import cl.bootcamp.apinews.state.NewsState
import cl.bootcamp.apinews.view.NewsDetailScreen
import cl.bootcamp.apinews.view.NewsListScreen
import cl.bootcamp.apinews.viewModel.NewsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavGraph(navController: NavHostController, viewModel: NewsViewModel, state: NewsState) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Noticias") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.Green)
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "news/{country}/{category}/{page}", // Agregar parámetros aquí
            Modifier.padding(innerPadding)
        ) {
            composable("news/{country}/{category}/{page}") { backStackEntry ->
                val country =
                    backStackEntry.arguments?.getString("country") ?: "us" // Valor por defecto
                val category = backStackEntry.arguments?.getString("category")
                    ?: "general" // Valor por defecto
                val page =
                    backStackEntry.arguments?.getString("page")?.toInt() ?: 1 // Valor por defecto

                NewsListScreen(navController, viewModel, country, category, page, state)
            }
            composable("detailsNews/{newsUrl}") { backStackEntry ->
                val newsUrl = backStackEntry.arguments?.getString("newsUrl") ?: return@composable
                val article =
                    state.newsList.find { it.url == newsUrl } // Asegúrate de que la URL existe
                if (article != null) {
                    NewsDetailScreen(navController, article)
                }
            }
        }
    }
}