package com.nicolascristaldo.cinefan.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nicolascristaldo.cinefan.R
import com.nicolascristaldo.cinefan.data.network.response.Movie
import com.nicolascristaldo.cinefan.ui.home.QueryUIState

class MovieAdapter(
    private var movies: List<Movie> = emptyList(),
    private val onItemSelected: (String) -> Unit
) : RecyclerView.Adapter<MovieViewHolder>() {

    fun updateList(uiState: QueryUIState.Success) {
        this.movies = if (uiState.response.isValidResult()) uiState.response.movies!!
        else emptyList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MovieViewHolder(layoutInflater.inflate(R.layout.item_movie, parent, false))
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.render(movies[position], onItemSelected)
    }

}