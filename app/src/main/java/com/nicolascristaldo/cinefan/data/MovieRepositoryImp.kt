package com.nicolascristaldo.cinefan.data

import com.nicolascristaldo.cinefan.data.network.MoviesApiService
import com.nicolascristaldo.cinefan.data.network.response.Movie
import com.nicolascristaldo.cinefan.data.network.response.QueryResponse
import com.nicolascristaldo.cinefan.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImp @Inject constructor(
    private val apiService: MoviesApiService
): MovieRepository {
    override suspend fun getMoviesByName(name: String, page: String): QueryResponse =
        apiService.getMoviesByName(name, page)

    override suspend fun getMovieById(id: String): Movie =
        apiService.getMovieById(id)
}