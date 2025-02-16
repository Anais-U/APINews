package cl.bootcamp.apinews.datasource

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import cl.bootcamp.apinews.model.DetailsNews
import android.content.Context
import androidx.room.TypeConverters
import cl.bootcamp.apinews.model.NewsDao

@Database(entities = [DetailsNews::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "news_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}