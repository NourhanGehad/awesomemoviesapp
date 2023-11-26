package com.example.awesomemoviesapp.movies.domain.model

import com.example.awesomemoviesapp.movies.data.model.Genre

data class MovieDetails(
    val id: Int,
    val isAdult: Boolean?,
    val title: String?,
    val releaseDate: String?,
    val backdropPathPath: String? = null,
    val genres: List<Genre>,
    val originalLanguage: String,
    val overview: String,
)
