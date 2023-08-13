package com.ptk.pweather.roomdb.dao

import androidx.room.Dao
import androidx.room.Query
import com.ptk.pweather.roomdb.entity.UserEntity

@Dao
interface UserDao {

    /*    @Query("SELECT count(id) FROM items")
        suspend fun numberOfItemsInDB() : Int*/

    @Query("INSERT INTO tbl_user VALUES(0,:username,:password)")
    suspend fun registerUser(username: String, password: String): Long

    @Query("SELECT * FROM tbl_user WHERE user_name=:username AND password=:password")
    suspend fun login(username: String, password: String): UserEntity?
}