package com.example.awesomemoviesapp.movies.presentation.moviedetails.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.awesomemoviesapp.movies.data.di.MainDispatcher
import com.example.awesomemoviesapp.movies.domain.usecase.GetMovieDetailsUseCase
import com.example.awesomemoviesapp.movies.presentation.moviedetails.model.MovieDetailsScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    @MainDispatcher private val dispatcher: CoroutineDispatcher,
) : ViewModel() {
    private var _state by mutableStateOf(
        MovieDetailsScreenState(
            movie = null,
            isLoading = true
        )
    )

    val state: State<MovieDetailsScreenState>
        get() = derivedStateOf { _state }

    private val errorHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
        _state = _state.copy(
            isLoading = false,
            error = throwable.message
        )
    }

    init {
        loadMovieDetails()
    }

    fun loadMovieDetails() = viewModelScope.launch(errorHandler + dispatcher) {
        getMovie(
            movieId = savedStateHandle.get<Int>("movie_id") ?: 0
        )
    }

    private suspend fun getMovie(movieId: Int) {
        val movieDetails = getMovieDetailsUseCase(movieId)
        _state = _state.copy(
            movie = movieDetails,
            isLoading = false,
            error = null
        )
    }

}