package com.nicolascristaldo.cinefan.ui.home

import androidx.lifecycle.ViewModel
import com.nicolascristaldo.cinefan.data.MovieRepositoryImp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val movieRepositoryImp: MovieRepositoryImp
) : ViewModel() {

    private var _queryUIState = MutableStateFlow<QueryUIState>(QueryUIState.Loading)
    val queryUIState: StateFlow<QueryUIState> = _queryUIState

    fun getMoviesByName(query: String, page: Int = 1) {
        CoroutineScope(Dispatchers.IO).launch {
            _queryUIState.value = QueryUIState.Loading
            _queryUIState.value = try {
                QueryUIState.Success(
                    movieRepositoryImp.getMoviesByName(
                        query.trim(' '),
                        page.toString()
                    )
                )
            } catch (e: IOException) {
                QueryUIState.Error("error")
            } catch (e: HttpException) {
                QueryUIState.Error("error")
            }
        }
    }

}