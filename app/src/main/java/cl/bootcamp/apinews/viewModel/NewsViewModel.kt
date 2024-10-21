package cl.bootcamp.apinews.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.bootcamp.apinews.repository.NewsRepository
import cl.bootcamp.apinews.state.NewsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val repository: NewsRepository
) : ViewModel() {

    private val _state = MutableLiveData<NewsState>(NewsState())
    val state: LiveData<NewsState> get() = _state

    fun fetchAllNews(country: String, category: String, page: Int) {
        viewModelScope.launch {
            _state.value = NewsState(isLoading = true) // Indica que la carga ha comenzado
            Log.d("NewsViewModel", "Fetching news for country: $country, category: $category, page: $page")

            try {
                val articles = repository.getTopHeadlines(country, category, page)
                _state.value = if (articles.isNotEmpty()) {
                    NewsState(newsList = articles) // Actualiza el estado con la lista de noticias
                } else {
                    NewsState(error = "No se encontraron artículos.")
                }
            } catch (e: HttpException) {
                _state.value = NewsState(error = "Error en la conexión con la API: ${e.message}")
                e.printStackTrace()
            } catch (e: IOException) {
                _state.value = NewsState(error = "Error de red, verifica tu conexión: ${e.message}")
                e.printStackTrace()
            } catch (e: Exception) {
                _state.value = NewsState(error = "Ocurrió un error inesperado: ${e.message}")
                e.printStackTrace()
            }
        }
    }

    // Nueva función para buscar noticias por consulta
    fun searchNews(query: String, page: Int) {
        viewModelScope.launch {
            _state.value = NewsState(isLoading = true) // Indica que la carga ha comenzado
            Log.d("NewsViewModel", "Searching news for query: $query, page: $page")

            try {
                val articles = repository.searchNews()
                _state.value = if (articles.isNotEmpty()) {
                    NewsState(newsList = articles) // Actualiza el estado con la lista de noticias
                } else {
                    NewsState(error = "No se encontraron artículos para la búsqueda.")
                }
            } catch (e: HttpException) {
                _state.value = NewsState(error = "Error en la conexión con la API: ${e.message}")
                e.printStackTrace()
            } catch (e: IOException) {
                _state.value = NewsState(error = "Error de red, verifica tu conexión: ${e.message}")
                e.printStackTrace()
            } catch (e: Exception) {
                _state.value = NewsState(error = "Ocurrió un error inesperado: ${e.message}")
                e.printStackTrace()
            }
        }
    }

    // Nueva función para obtener un artículo por su URL
    fun getArticleByUrl(url: String) {
        viewModelScope.launch {
            try {
                val article = repository.getArticleByUrl(url)
                if (article != null) {
                    // Manejar el artículo obtenido (por ejemplo, actualizar el estado)
                    // Aquí podrías tener otro estado en tu NewsState si es necesario
                } else {
                    _state.value = NewsState(error = "Artículo no encontrado.")
                }
            } catch (e: Exception) {
                _state.value = NewsState(error = "Ocurrió un error inesperado al obtener el artículo: ${e.message}")
                e.printStackTrace()
            }
        }
    }

    fun resetError() {
        _state.value = _state.value?.copy(error = null)

    }
}