package com.ptk.pweather.roomdb.database_obj

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ptk.pweather.roomdb.dao.UserDao
import com.ptk.pweather.roomdb.entity.UserEntity

@Database(
    entities = [UserEntity::class],
    version = 1,
    exportSchema = false
)
abstract class PWeatherDB : RoomDatabase() {

    abstract fun getUserDao(): UserDao
}