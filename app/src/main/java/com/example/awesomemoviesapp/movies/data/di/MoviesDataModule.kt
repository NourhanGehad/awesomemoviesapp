package com.example.awesomemoviesapp.movies.data.di

import com.example.awesomemoviesapp.movies.data.MoviesApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object MoviesDataModule {

    @Provides
    fun provideMoviesApiService(
        retrofit: Retrofit
    ): MoviesApiService {
        return retrofit.create(MoviesApiService::class.java)
    }
}
