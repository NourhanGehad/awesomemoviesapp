package com.example.awesomemoviesapp.movies.data.model

data class MoviesResponse(
    var page: String? = null,
    var results: List<RemoteMovie>,
)