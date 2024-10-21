package cl.bootcamp.apinews.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "details_news")
data class DetailsNews(
    @PrimaryKey // Si decides mantener el id, no uses autoGenerate
    val id: Int = 0, // Este puede ser opcional si decides usar la URL
    val author: String?,
    val title: String,
    val description: String? = null,
    val url: String,
    val urlToImage: String? = null,
    val publishedAt: String? = null,
    val content: String? = null
)