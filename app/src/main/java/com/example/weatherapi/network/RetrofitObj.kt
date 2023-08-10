package com.example.weatherapi.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitObj {

    fun provideRetrofit(): APIService {
        return Retrofit.Builder()
            .baseUrl("https:google.com")
            .addConverterFactory(GsonConverterFactory.create())
            .client(createClient())
            .build()
            .create(APIService::class.java)
    }

    private fun createLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return logging
    }

    fun createClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(createLoggingInterceptor())
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()
    }

}