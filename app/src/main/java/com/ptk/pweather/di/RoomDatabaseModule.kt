package com.ptk.pweather.di

import android.content.Context
import androidx.room.Room
import com.ptk.pweather.roomdb.database_obj.PWeatherDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomDatabaseModule {

    @Singleton
    @Provides
    fun providePWeatherDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        PWeatherDB::class.java,
        "p_weather.db"
    ).build()

    @Singleton
    @Provides
    fun provideUserDao(db: PWeatherDB) = db.getUserDao()

}