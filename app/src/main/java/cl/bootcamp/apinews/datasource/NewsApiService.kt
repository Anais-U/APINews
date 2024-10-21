package cl.bootcamp.apinews.datasource


import cl.bootcamp.apinews.model.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface NewsApiService {

    // Este endpoint obtiene todas las noticias
    @GET("v2/top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String,
        @Query("category") category: String,
        @Query("page") page: Int
    ): Response<NewsResponse>

    // Este endpoint busca noticias por palabra clave
    @GET("v2/everything")
    suspend fun searchNews(
        @Query("q") query: String,
        @Query("page") page: Int
    ): Response<NewsResponse>

    @GET
    suspend fun getEverything(@Url url: String):
            Response<NewsResponse>
}