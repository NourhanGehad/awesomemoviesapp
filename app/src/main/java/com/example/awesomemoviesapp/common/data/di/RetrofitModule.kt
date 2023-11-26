package com.example.awesomemoviesapp.common.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .client(client())
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    internal fun client(): OkHttpClient =
        OkHttpClient()
            .newBuilder()
            .addInterceptor(
                Interceptor {
                    val requestBuilder = it.request().newBuilder()
                        .addHeader("accept", "application/json")
                        .addHeader(
                            "Authorization",
                            "Bearer " + "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIyOTI0ZDZkMDFhZmE3OTA2OTIxMTE2MjI1NDU5NjZhYyIsInN1YiI6IjVjYzNmZmU5YzNhMzY4MDlhYjg4YTI0NyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.JKwVwBZOfkfUMnOzFl3XA2zfcxkVYXiSF5m0vkQzchI"
                        )
                    return@Interceptor it.proceed(requestBuilder.build())
                }
            )
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    setLevel(HttpLoggingInterceptor.Level.BODY)
                }
            )
            .build()

}