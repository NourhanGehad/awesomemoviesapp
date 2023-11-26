package com.example.awesomemoviesapp.movies.presentation.movieslist.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.awesomemoviesapp.configuration.data.ConfigurationRepository
import com.example.awesomemoviesapp.movies.data.MoviesPagingSource
import com.example.awesomemoviesapp.movies.data.di.MainDispatcher
import com.example.awesomemoviesapp.movies.presentation.movieslist.model.MoviesScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val moviesPagingSource: MoviesPagingSource,
    private val configurationRepository: ConfigurationRepository,
    @MainDispatcher private val dispatcher: CoroutineDispatcher,
) : ViewModel() {

    private var _state by mutableStateOf(
        MoviesScreenState(
            movies = emptyFlow(),
            isLoading = true
        )
    )

    val state: State<MoviesScreenState>
        get() = derivedStateOf { _state }

    private val errorHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
        _state = _state.copy(
            isLoading = false,
            error = throwable.message
        )
    }

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch(errorHandler + dispatcher) {
            fetchPosterBaseUrl()
            fetchMoviesList()
        }
    }

    private suspend fun fetchPosterBaseUrl() {
        val posterBaseUrl = configurationRepository.getPosterBaseUrl()
        _state = _state.copy(
            posterBaseUrl = posterBaseUrl,
            error = null
        )
    }

    private fun fetchMoviesList() {
        _state = _state.copy(
            movies = Pager(
                pagingSourceFactory = {
                    moviesPagingSource
                },
                config = PagingConfig(pageSize = 10)
            ).flow.cachedIn(viewModelScope),
            isLoading = false,
            error = null
        )
    }
}