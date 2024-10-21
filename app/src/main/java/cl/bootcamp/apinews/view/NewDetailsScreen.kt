package cl.bootcamp.apinews.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import cl.bootcamp.apinews.model.DetailsNews

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsDetailScreen(navController: NavController, article: DetailsNews) {
    Column(modifier = Modifier.padding(16.dp)) {
        TopAppBar(
            title = { Text("Detalles de la Noticia") },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Regresar")
                }
            }
        )
        Text(text = article.title, style = MaterialTheme.typography.headlineLarge)
        Text(text = "Por: ${article.author ?: "Desconocido"}", style = MaterialTheme.typography.bodyMedium)
        Text(text = article.content ?: "Contenido no disponible.", style = MaterialTheme.typography.bodyMedium)
    }
}