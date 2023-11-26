package com.example.awesomemoviesapp.movies.data

import com.example.awesomemoviesapp.movies.data.model.MovieDetailsResponse
import com.example.awesomemoviesapp.movies.data.model.MoviesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApiService {
    @GET("discover/movie")
    suspend fun getMovies(
        @Query("page") pageNumber: Int
    ): MoviesResponse

    @GET("movie/{movieId}")
    suspend fun getMovieDetails(
        @Path("movieId") movieId: Int,
    ): MovieDetailsResponse
}