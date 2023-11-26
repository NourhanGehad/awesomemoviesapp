package com.example.awesomemoviesapp.movies.domain.usecase

import com.example.awesomemoviesapp.configuration.data.ConfigurationRepository
import com.example.awesomemoviesapp.movies.data.MovieDetailsDataSource
import com.example.awesomemoviesapp.movies.data.di.IODispatcher
import com.example.awesomemoviesapp.movies.data.model.MovieDetailsResponse
import com.example.awesomemoviesapp.movies.domain.model.MovieDetails
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(
    private val configurationRepository: ConfigurationRepository,
    private val movieDetailsDataSource: MovieDetailsDataSource,
    @IODispatcher private val dispatcher: CoroutineDispatcher,
) {

    suspend operator fun invoke(movieId: Int) = withContext(dispatcher) {
        val movieDetailsResponse = movieDetailsDataSource.loadMovieDetails(movieId)
        val imageBaseUrl = configurationRepository.getMovieImageBaseUrl()

        return@withContext MovieDetails(
            id = movieDetailsResponse.id,
            isAdult = movieDetailsResponse.adult,
            title = getTitle(movieDetailsResponse),
            releaseDate = movieDetailsResponse.releaseDate,
            genres = movieDetailsResponse.genres,
            backdropPathPath = imageBaseUrl + movieDetailsResponse.backdropPath,
            originalLanguage = movieDetailsResponse.originalLanguage,
            overview = movieDetailsResponse.overview
        )
    }

    private fun getTitle(movieDetailsResponse: MovieDetailsResponse) =
        if (movieDetailsResponse.originalLanguage == "en") {
            movieDetailsResponse.originalTitle
        } else {
            movieDetailsResponse.originalTitle + " ( " + movieDetailsResponse.title + " ) "
        }
}