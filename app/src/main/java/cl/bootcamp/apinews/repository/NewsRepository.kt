package cl.bootcamp.apinews.repository


import cl.bootcamp.apinews.datasource.NewsApiService
import cl.bootcamp.apinews.model.DetailsNews
import cl.bootcamp.apinews.model.NewsDao
import cl.bootcamp.apinews.model.NewsResponse
import cl.bootcamp.apinews.util.Constants
import retrofit2.Response
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val apiService: NewsApiService,
    private val newsDao: NewsDao // Inyectamos el DAO
) {

    private fun buildUrl(): String {
        return "${Constants.BASE_URL}${Constants.ENDPOINT}?q=${Constants.QUERY}&from=${Constants.FROM_DATE}&sortBy=${Constants.SORT_BY}&apiKey=${Constants.API_KEY}"
    }

    suspend fun getTopHeadlines(country: String, category: String, page: Int): List<DetailsNews> {
        return fetchArticles { apiService.getTopHeadlines(country, category, page) }
    }

    suspend fun searchNews(): List<DetailsNews> {
        val url = buildUrl()
        return fetchArticles { apiService.getEverything(url) }
    }

    private suspend fun fetchArticles(apiCall: suspend () -> Response<NewsResponse>): List<DetailsNews> {
        return try {
            val response = apiCall()
            if (response.isSuccessful) {
                response.body()?.articles?.let { articles ->
                    newsDao.insertAll(articles) // Guardar en la base de datos
                    return newsDao.getAllArticles() // Retornar los artículos desde la base de datos
                } ?: emptyList() // Si no hay artículos, retornar lista vacía
            } else {
                throw Exception("Error en la respuesta de la API: ${response.message()}")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList() // Retorna lista vacía en caso de error
        }
    }

    // Método para obtener un artículo por su URL
    suspend fun getArticleByUrl(url: String): DetailsNews? {
        return newsDao.getArticleByUrl(url) // Asegúrate de que este método esté implementado en el DAO
    }
}