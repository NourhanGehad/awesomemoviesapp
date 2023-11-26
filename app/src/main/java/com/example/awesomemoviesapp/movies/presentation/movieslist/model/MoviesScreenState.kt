package com.example.awesomemoviesapp.movies.presentation.movieslist.model

import androidx.paging.PagingData
import com.example.awesomemoviesapp.movies.domain.model.Movie
import kotlinx.coroutines.flow.Flow

data class MoviesScreenState(
    val movies: Flow<PagingData<Movie>>,
    val posterBaseUrl: String? = null,
    val isLoading: Boolean,
    val error: String? = null,
)