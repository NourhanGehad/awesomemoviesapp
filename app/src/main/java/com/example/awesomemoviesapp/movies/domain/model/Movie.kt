package com.example.awesomemoviesapp.movies.domain.model

data class Movie(
    val id: Int,
    val adult: Boolean?,
    val name: String?,
    val releaseYear: String?,
    val posterImagePath: String? = null,
)
