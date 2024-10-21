package cl.bootcamp.apinews.di

import android.app.Application
import cl.bootcamp.apinews.datasource.AppDatabase
import cl.bootcamp.apinews.datasource.NewsApiService
import cl.bootcamp.apinews.datasource.RetrofitInstance
import cl.bootcamp.apinews.repository.NewsRepository
import cl.bootcamp.apinews.util.Constants.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    @Provides
    @Singleton
    fun provideNewsApiService(): NewsApiService {
        return RetrofitInstance.api
    }

    @Provides
    @Singleton
    fun provideAppDatabase(application: Application): AppDatabase {
        return AppDatabase.getDatabase(application.applicationContext) // Usar el contexto de la aplicación
    }

    @Provides
    @Singleton
    fun provideNewsRepository(
        apiService: NewsApiService,
        appDatabase: AppDatabase
    ): NewsRepository {
        return NewsRepository(apiService,appDatabase.newsDao())
    }
}