package com.nicolascristaldo.cinefan.ui.home

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
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
import com.nicolascristaldo.cinefan.R
import com.nicolascristaldo.cinefan.databinding.ActivityMainBinding
import com.nicolascristaldo.cinefan.ui.detail.DetailActivity
import com.nicolascristaldo.cinefan.ui.detail.DetailActivity.Companion.EXTRA_ID
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
        movieAdapter = MovieAdapter { navigateToDetail(it) }
        binding.rvMovies.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(context, 2)
            adapter = movieAdapter
        }
    }

    private fun initListeners() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (isFirstTime) {
                    isFirstTime = false
                    binding.searchMovieView.isVisible = false
                    binding.progressBar.isVisible = true
                }
                viewModel.currentPage = 1
                viewModel.currentQuery = query.orEmpty()
                viewModel.getMoviesByName()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean = false
        })

        binding.ivPageSearchBack.setOnClickListener {
            viewModel.goToPreviousPage()
        }

        binding.ivPageSearchForward.setOnClickListener {
            viewModel.goToNextPage()
        }
    }

    private fun initUiState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.queryUIState.collect { state ->
                    when (state) {
                        is QueryUIState.Error -> errorState()
                        QueryUIState.Loading -> loadingState()
                        is QueryUIState.Success -> successState(state)
                    }
                }
            }
        }
    }

    private fun successState(queryUIState: QueryUIState.Success) {
        binding.progressBar.isVisible = false
        binding.pageNavigationBar.isVisible = queryUIState.response.isValidResult()
        movieAdapter.updateList(queryUIState)
        createUI(queryUIState)
    }

    private fun loadingState() {
        binding.pageNavigationBar.isVisible = false
        if (!isFirstTime) binding.progressBar.isVisible = true
        else binding.searchMovieView.isVisible = true
    }

    private fun errorState() {
        binding.pageNavigationBar.isVisible = false
        binding.searchMovieView.isVisible = false
        binding.progressBar.isVisible = false
        movieAdapter.cleanList()
        Toast.makeText(
            binding.toolbar.context,
            getString(R.string.error_occurred),
            Toast.LENGTH_LONG
        ).show()
    }

    private fun navigateToDetail(id: String) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(EXTRA_ID, id)
        startActivity(intent)
    }

    private fun createUI(queryUIState: QueryUIState.Success) {
        binding.tvPages.text =  getString(
            R.string.search_page,
            viewModel.currentPage,
            queryUIState.response.totalPages()
        )

    }


}