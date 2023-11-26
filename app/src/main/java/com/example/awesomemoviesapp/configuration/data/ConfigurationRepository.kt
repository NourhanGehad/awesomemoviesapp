package com.example.awesomemoviesapp.configuration.data

import android.content.Context
import com.example.awesomemoviesapp.configuration.data.model.ConfigurationsResponse
import com.example.awesomemoviesapp.configuration.domain.ConfigurationData
import com.example.awesomemoviesapp.configuration.domain.ImageConfiguration
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConfigurationRepository @Inject constructor(
    private val remoteDataSource: ConfigurationRemoteDataSource,
    @ApplicationContext private val context: Context,
) {

    private val configurationFileName = "configuration_data"

    suspend fun getPosterBaseUrl(): String {
        val config = getConfiguration()
        return "${config.imagesConfig.baseUrl}${config.imagesConfig.posterSizes.last()}"
    }

    suspend fun getMovieImageBaseUrl(): String {
        val config = getConfiguration()
        return "${config.imagesConfig.baseUrl}${config.imagesConfig.backdropSizes.last()}"
    }

    private suspend fun getConfiguration(): ConfigurationData {
        val localConfiguration = getLocalConfiguration()

        return if (localConfiguration != null && isDataFresh(localConfiguration.lastUpdated)) {
            // Return local configuration if available and fresh
            localConfiguration
        } else {
            // Fetch remote configuration if not available or not fresh
            val configuration = remoteDataSource.getConfiguration().toConfigurationData()

            // Save remote configuration locally
            saveLocalConfiguration(configuration)

            configuration
        }
    }

    private fun getLocalConfiguration(): ConfigurationData? {
        return try {
            val file = File(context.filesDir, configurationFileName)

            if (file.exists()) {
                ObjectInputStream(FileInputStream(file)).use {
                    it.readObject() as ConfigurationData
                }
            } else {
                null
            }
        } catch (ex: Exception) {
            null
        }
    }

    private fun saveLocalConfiguration(configurationData: ConfigurationData) {
        try {
            val file = File(context.filesDir, configurationFileName)
            ObjectOutputStream(FileOutputStream(file)).use {
                it.writeObject(configurationData)
            }
        } catch (ex: Exception) {
            // Handle the exception (e.g., log or show an error)
        }
    }

    private fun isDataFresh(lastUpdated: Date?): Boolean {
        if (lastUpdated == null) {
            return false
        }

        val currentTime = System.currentTimeMillis()
        val lastUpdatedTime = lastUpdated.time
        val elapsedTime = currentTime - lastUpdatedTime
        val twentyFourHoursInMillis = 24 * 60 * 60 * 1000

        return elapsedTime < twentyFourHoursInMillis
    }

    private fun ConfigurationsResponse.toConfigurationData() = ConfigurationData (
        imagesConfig = ImageConfiguration (
            baseUrl = this.images.baseUrl,
            posterSizes = this.images.posterSizes,
            backdropSizes = this.images.backdropSizes
        ),
        lastUpdated = Date(System.currentTimeMillis())
    )
}