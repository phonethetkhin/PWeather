package com.ptk.pweather.roomdb.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ptk.pweather.roomdb.entity.UserEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun registerUser(userEntity: UserEntity): Long

    @Query("SELECT * FROM tbl_user WHERE user_name =:userName")
    suspend fun checkUserNameExist(userName: String): List<UserEntity>


    @Query("SELECT * FROM tbl_user WHERE user_name=:username AND password=:password")
    suspend fun login(username: String, password: String): List<UserEntity>
}