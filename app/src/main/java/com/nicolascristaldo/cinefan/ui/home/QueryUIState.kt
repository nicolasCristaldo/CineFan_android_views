package com.nicolascristaldo.cinefan.ui.home

import com.nicolascristaldo.cinefan.data.network.response.QueryResponse

sealed class QueryUIState {
    data object Loading: QueryUIState()
    data class Error(val error: String): QueryUIState()
    data class Success(val response: QueryResponse): QueryUIState()
}