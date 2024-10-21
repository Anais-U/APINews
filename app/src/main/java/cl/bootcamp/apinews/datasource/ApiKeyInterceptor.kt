package cl.bootcamp.apinews.datasource

import android.util.Log
import cl.bootcamp.apinews.util.Constants
import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor : Interceptor {  // Eliminar el parámetro apiKey
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url

        // Agregar el parámetro de API Key a la URL con formato ?key=api_key
        val urlWithApiKey = originalUrl.newBuilder()
            .addQueryParameter("apiKey", Constants.API_KEY)  // Utiliza Constants.API_KEY
            .build()

        // Crear una nueva solicitud con la URL actualizada
        val newRequest = originalRequest.newBuilder()
            .url(urlWithApiKey)
            .build()

        Log.d("ApiKeyInterceptor", "Request URL: $ {newRequest.url}")

        return chain.proceed(newRequest)
    }
}