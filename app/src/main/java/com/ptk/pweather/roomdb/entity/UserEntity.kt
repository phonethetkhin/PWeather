package com.ptk.pweather.roomdb.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_user")
data class UserEntity(
    @ColumnInfo(name = "user_name") val userName: String,
    @ColumnInfo(name = "password") val password: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}