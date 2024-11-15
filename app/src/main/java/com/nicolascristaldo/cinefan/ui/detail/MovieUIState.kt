package com.nicolascristaldo.cinefan.ui.detail

import com.nicolascristaldo.cinefan.data.network.response.Movie

sealed class MovieUIState {
    data object Loading: MovieUIState()
    data class Error(val error: String): MovieUIState()
    data class Success(val movie: Movie): MovieUIState()
}