package com.nicolascristaldo.cinefan.domain.repository

import com.nicolascristaldo.cinefan.data.network.response.Movie
import com.nicolascristaldo.cinefan.data.network.response.QueryResponse

interface MovieRepository {
    suspend fun getMoviesByName(name: String, page: String): QueryResponse
    suspend fun getMovieById(id: String): Movie
}