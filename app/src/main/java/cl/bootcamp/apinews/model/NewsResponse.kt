package cl.bootcamp.apinews.model


data class NewsResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<DetailsNews>
)