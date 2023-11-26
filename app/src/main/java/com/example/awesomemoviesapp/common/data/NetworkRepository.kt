package com.example.awesomemoviesapp.common.data.di

import android.util.Log
import retrofit2.Call
import retrofit2.Retrofit
import java.io.IOException
import javax.inject.Inject

class NetworkRepository @Inject constructor(private val retrofit: Retrofit) {
    suspend fun <T : Any> executeCall(call: Call<T>): T {
        try {
            val response = call.execute()
            if (response.isSuccessful) {
                return response.body()!!
            } else {
                // Handle unsuccessful response
                throw IOException("Error ${response.code()}: ${response.errorBody()?.string()}")
            }
        } catch (e: IOException) {
            // Handle network-related exceptions
            Log.e("RetrofitModule", "Network error: ${e.message}")
            throw e
        } catch (e: Exception) {
            // Handle other exceptions
            Log.e("RetrofitModule", "Error: ${e.message}")
            throw e
        }
    }
}