package com.example.awesomemoviesapp.movies.data

import com.example.awesomemoviesapp.movies.data.di.IODispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieDetailsDataSource @Inject constructor(
    private val apiService: MoviesApiService,
    @IODispatcher private val dispatcher: CoroutineDispatcher,
) {
    suspend fun loadMovieDetails(movieId: Int) = withContext(dispatcher) {
        try {
            return@withContext apiService.getMovieDetails(movieId)
        } catch (ex: Exception) {
            throw Exception("Something went wrong. Couldn't load movie details, try connecting to internet.")
        }
    }
}