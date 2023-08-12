package com.example.weatherapi.di

import com.example.weatherapi.network.APIService
import com.example.weatherapi.network.RetrofitObj
import com.example.weatherapi.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiServiceModule {

    @Singleton
    @Provides
    fun provideRetrofit(): APIService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(RetrofitObj.createClient())
            .build()
            .create(APIService::class.java)
    }


}
