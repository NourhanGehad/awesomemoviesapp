package com.example.awesomemoviesapp.configuration.data

import com.example.awesomemoviesapp.movies.data.di.IODispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ConfigurationRemoteDataSource @Inject constructor(
    private val apiService: ConfigurationApiService,
    @IODispatcher private val dispatcher: CoroutineDispatcher,
) {
    suspend fun getConfiguration() = withContext(dispatcher) {
        try {
            apiService.getConfiguration()
        } catch (ex: Exception) {
            throw Exception("Something went wrong. Couldn't load Configurations.")
        }
    }
}