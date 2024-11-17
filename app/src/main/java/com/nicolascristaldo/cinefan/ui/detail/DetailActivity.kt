package com.nicolascristaldo.cinefan.ui.detail

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.nicolascristaldo.cinefan.R
import com.nicolascristaldo.cinefan.data.network.response.Movie
import com.nicolascristaldo.cinefan.data.providers.RatingProvider
import com.nicolascristaldo.cinefan.databinding.ActivityDetailBinding
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_ID = "extra_id"
    }

    private lateinit var binding: ActivityDetailBinding

    private val viewModel: DetailActivityViewModel by viewModels()

    @Inject
    lateinit var ratingProvider: RatingProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val id = intent.getStringExtra(EXTRA_ID).orEmpty()
        viewModel.getMovie(id)
        initUI()
    }

    private fun initUI() {
        initUIState()
        initListeners()
    }

    private fun initListeners() {
        binding.ivArrowBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun createUI(movie: Movie) {
        val rating = ratingProvider.getRating(movie.totalRating())
        Picasso.get().load(movie.imageUrl).into(binding.ivMovie)
        binding.tvType.text = getString(R.string.movie_type, movie.type)
        binding.tvYear.text = getString(R.string.movie_year, movie.year)
        binding.tvGenre.text = getString(R.string.movie_genre, movie.genre)
        binding.tvRuntime.text = getString(R.string.movie_runtime, movie.runtime)
        binding.tvPlot.text = movie.plot
        binding.tvLanguage.text = getString(R.string.movie_language, movie.language)
        binding.tvVotes.text = getString(R.string.movie_votes, movie.votes)
        binding.tvTitle.text = movie.title
        binding.tvRating.text = movie.rating
        binding.tvRating.setTextColor(getColor(rating.color))
        binding.ivRating.setImageResource(rating.image)
        binding.cvRating.isVisible = true
        binding.tvContributors.text = getString(R.string.key_contributors)
        binding.tvDirector.text = getString(R.string.movie_director, movie.director)
        binding.tvWriter.text = getString(R.string.movie_writer, movie.writer)
        binding.tvActors.text = getString(R.string.movie_actors, movie.actors)
    }

    private fun initUIState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.movieState.collect { state ->
                    when (state) {
                        is MovieUIState.Error -> errorState()
                        MovieUIState.Loading -> loadingState()
                        is MovieUIState.Success -> successState(state)
                    }
                }
            }
        }
    }

    private fun loadingState() {
        binding.progressBar.isVisible = true
    }

    private fun successState(state: MovieUIState.Success) {
        binding.progressBar.isVisible = false
        createUI(state.movie)
    }

    private fun errorState() {
        binding.progressBar.isVisible = false
        Toast.makeText(
            binding.toolbar.context,
            getString(R.string.error_occurred),
            Toast.LENGTH_LONG
        ).show()
    }

}