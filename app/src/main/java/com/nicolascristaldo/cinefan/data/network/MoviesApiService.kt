package com.nicolascristaldo.cinefan.data.network

import com.nicolascristaldo.cinefan.data.network.response.Movie
import com.nicolascristaldo.cinefan.data.network.response.QueryResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApiService {
    @GET("/?apikey=a13b64b0&type=movie")
    suspend fun getMoviesByName(@Query("s") name: String, @Query("page") page: String): QueryResponse

    @GET("/?apikey=a13b64b0&i={id}&plot=full")
    suspend fun getMovieById(@Path("id") id: String): Movie


}