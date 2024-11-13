package com.nicolascristaldo.cinefan.ui.home.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.nicolascristaldo.cinefan.data.network.response.Movie
import com.nicolascristaldo.cinefan.databinding.ItemMovieBinding
import com.squareup.picasso.Picasso

class MovieViewHolder(view: View): RecyclerView.ViewHolder(view) {
    private val binding = ItemMovieBinding.bind(view)

    fun render(
        movie: Movie,
        onItemSelected: (String) -> Unit
    ) {
        binding.tvMovieName.text = movie.title
        Picasso.get().load(movie.imageUrl).into(binding.ivMovie)
        binding.root.setOnClickListener {
            onItemSelected(movie.id)
        }
    }
}