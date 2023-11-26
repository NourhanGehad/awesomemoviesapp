package com.example.awesomemoviesapp.movies.presentation.moviedetails.model

import com.example.awesomemoviesapp.movies.domain.model.MovieDetails

data class MovieDetailsScreenState(
    val movie: MovieDetails?,
    val isLoading: Boolean,
    val error: String? = null,
)