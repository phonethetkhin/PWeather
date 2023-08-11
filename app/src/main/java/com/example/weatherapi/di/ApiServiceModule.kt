package com.example.weatherapi.di

import android.app.Application
import com.example.weatherapi.network.APIService
import com.example.weatherapi.network.RetrofitObj
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiServiceModule {

    @Singleton
    @Provides
    fun provideRetrofit(): APIService {
        return Retrofit.Builder()
            .baseUrl("https:google.com")
            .addConverterFactory(GsonConverterFactory.create())
            .client(RetrofitObj.createClient())
            .build()
            .create(APIService::class.java)
    }


}