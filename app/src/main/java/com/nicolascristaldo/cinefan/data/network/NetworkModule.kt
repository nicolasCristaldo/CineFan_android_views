package com.nicolascristaldo.cinefan.data.network

import com.nicolascristaldo.cinefan.data.MovieRepositoryImp
import com.nicolascristaldo.cinefan.domain.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://www.omdbapi.com/"

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideMovieApiService(retrofit: Retrofit): MoviesApiService {
        return retrofit.create(MoviesApiService::class.java)
    }

    @Provides
    fun provideRepository(apiService: MoviesApiService): MovieRepository {
        return MovieRepositoryImp(apiService)
    }
}