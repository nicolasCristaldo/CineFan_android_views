package com.nicolascristaldo.cinefan.ui.detail

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
class DetailActivityViewModel @Inject constructor(
    private val movieRepositoryImp: MovieRepositoryImp
) : ViewModel() {

    private var _movieState = MutableStateFlow<MovieUIState>(MovieUIState.Loading)
    val movieState: StateFlow<MovieUIState> = _movieState


    fun getMovie(id: String) {
        CoroutineScope(Dispatchers.IO).launch {
            _movieState.value = MovieUIState.Loading
            _movieState.value = try {
                MovieUIState.Success(
                    movieRepositoryImp.getMovieById(id)
                )
            } catch (e: IOException) {
                MovieUIState.Error("error")
            } catch (e: HttpException) {
                MovieUIState.Error("error")
            }
        }
    }
}