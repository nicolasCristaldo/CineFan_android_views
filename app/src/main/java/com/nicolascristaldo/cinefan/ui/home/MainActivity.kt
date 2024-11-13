package com.nicolascristaldo.cinefan.ui.home

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.nicolascristaldo.cinefan.R
import com.nicolascristaldo.cinefan.databinding.ActivityMainBinding
import com.nicolascristaldo.cinefan.ui.home.adapter.MovieAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainActivityViewModel by viewModels()

    private lateinit var movieAdapter: MovieAdapter

    private var isFirstTime = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initUI()


    }

    private fun initUI() {
        initAdapters()
        initListeners()
        initUiState()
    }

    private fun initAdapters() {
        movieAdapter = MovieAdapter { hola() }
        binding.rvMovies.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(context, 2)
            adapter = movieAdapter
        }
    }

    private fun initUiState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.queryUIState.collect {
                    when (it) {
                        is QueryUIState.Error -> errorState()
                        QueryUIState.Loading -> loadingState()
                        is QueryUIState.Success -> successState(it)
                    }
                }
            }
        }
    }

    private fun successState(queryUIState: QueryUIState.Success) {
        binding.progressBar.isVisible = false
        movieAdapter.updateList(queryUIState)
    }

    private fun loadingState() {
        if (!isFirstTime) binding.progressBar.isVisible = true
    }

    private fun errorState() {
        binding.progressBar.isVisible = true
    }

    private fun hola() {

    }

    private fun initListeners() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (isFirstTime) {
                    isFirstTime = false
                    binding.progressBar.isVisible = true
                }
                viewModel.getMoviesByName(query.orEmpty())
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean = false
        })


    }


}