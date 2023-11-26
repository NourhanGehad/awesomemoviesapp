package com.example.awesomemoviesapp.configuration.domain

import java.io.Serializable
import java.util.Date

data class ConfigurationData(
    val imagesConfig: ImageConfiguration,
    val lastUpdated: Date
) : Serializable

data class ImageConfiguration(
    val baseUrl: String,
    val backdropSizes: List<String>,
    val posterSizes: List<String>
) : Serializable