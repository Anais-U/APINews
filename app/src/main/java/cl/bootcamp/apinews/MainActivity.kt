package cl.bootcamp.apinews

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.compose.rememberNavController
import cl.bootcamp.apinews.navigation.AppNavGraph
import cl.bootcamp.apinews.state.NewsState
import cl.bootcamp.apinews.ui.theme.APINewsTheme
import cl.bootcamp.apinews.viewModel.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val newsViewModel: NewsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            APINewsTheme {
                val navController = rememberNavController()

                // Observa el estado del ViewModel
                val state by newsViewModel.state.observeAsState(NewsState())

                // Llama a la API al iniciar la actividad
                LaunchedEffect(Unit) {
                    newsViewModel.fetchAllNews("us", "technology", 1) // Cambia los parámetros según tu necesidad
                }

                // Muestra un mensaje de error si está presente
                state.error?.let { errorMessage ->
                    Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()

                    // Restablece el error a null para no mostrarlo nuevamente
                    newsViewModel.resetError()  // Implementa este método en el ViewModel
                }

                // Pasa el estado al gráfico de navegación
                AppNavGraph(navController = navController, viewModel = newsViewModel, state = state)
            }
        }
    }
}