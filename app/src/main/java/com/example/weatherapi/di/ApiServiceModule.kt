package com.example.weatherapi.di

import android.app.Application
import com.example.weatherapi.network.RetrofitObj
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiServiceModule {

    @Singleton
    @Provides
    fun provideRetrofit(application: Application) = RetrofitObj.provideRetrofit()
}