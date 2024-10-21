package cl.bootcamp.apinews.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(articles: List<DetailsNews>)

    @Query("SELECT * FROM details_news")
    suspend fun getAllArticles(): List<DetailsNews>

    @Query("SELECT * FROM details_news WHERE url = :url LIMIT 1")
    suspend fun getArticleByUrl(url: String): DetailsNews?
}