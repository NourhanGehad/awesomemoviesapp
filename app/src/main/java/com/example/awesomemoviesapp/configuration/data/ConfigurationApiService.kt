package com.example.awesomemoviesapp.configuration.data

import com.example.awesomemoviesapp.configuration.data.model.ConfigurationsResponse
import retrofit2.http.GET

interface ConfigurationApiService {
    @GET("configuration")
    suspend fun getConfiguration(): ConfigurationsResponse
}