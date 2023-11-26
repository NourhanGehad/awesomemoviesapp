package com.example.awesomemoviesapp.configuration.data.di

import com.example.awesomemoviesapp.configuration.data.ConfigurationApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object ConfigurationModule {

    @Provides
    fun provideConfigurationApiService(
        retrofit: Retrofit
    ): ConfigurationApiService {
        return retrofit.create(ConfigurationApiService::class.java)
    }
}