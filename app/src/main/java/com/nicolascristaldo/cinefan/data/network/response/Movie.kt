package com.nicolascristaldo.cinefan.data.network.response

import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("Poster") val imageUrl: String,
    @SerializedName("Title") val title: String,
    @SerializedName("Type") val type: String,
    @SerializedName("Year") val year: String,
    @SerializedName("imdbID") val id: String,
    @SerializedName("Actors") val actors: String,
    @SerializedName("Director") val director: String,
    @SerializedName("Genre") val genre: String,
    @SerializedName("Language") val language: String,
    @SerializedName("Plot") val plot: String,
    @SerializedName("Runtime") val runtime: String,
    @SerializedName("Writer") val writer: String,
    @SerializedName("imdbRating") val rating: String,
    @SerializedName("imdbVotes") val votes: String
) {
    fun totalRating(): Float {
        return try {
            rating.toFloat()
        }
        catch (e: Exception) {
            0f
        }
    }
}