package com.nicolascristaldo.cinefan.data.network.response

import com.google.gson.annotations.SerializedName
import kotlin.math.ceil

data class QueryResponse(
    @SerializedName("Response") private val response: String,
    @SerializedName("Search") val movies: List<Movie>?,
    @SerializedName("totalResults") private val results: String?,
    @SerializedName("Error") val error: String?
) {
    fun totalResults() = results?.toInt() ?: 0

    fun totalPages(): Int {
        var pages = 0

        if (totalResults() != 0) {
            pages = ceil((totalResults() / 10).toDouble()).toInt()
        }

        return pages
    }

    fun isValidResult() = response.toBoolean()
}