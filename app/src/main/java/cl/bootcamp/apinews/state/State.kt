package cl.bootcamp.apinews.state

import cl.bootcamp.apinews.model.DetailsNews

data class NewsState(
    val isLoading: Boolean = false,
    val newsList: List<DetailsNews> = emptyList(),
    val error: String? = null,
    val selectedNews: DetailsNews? = null // Para manejar una noticia seleccionada
)