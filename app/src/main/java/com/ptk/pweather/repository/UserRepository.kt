package com.ptk.pweather.repository

import android.app.Application
import com.ptk.pweather.model.RemoteResource
import com.ptk.pweather.network.APIService
import com.ptk.pweather.roomdb.dao.UserDao
import com.ptk.pweather.roomdb.entity.UserEntity
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.channelFlow
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userDao: UserDao,
    private val application: Application,
    private val apiService: APIService,

    ) {
    fun checkUserName(userName: String) = channelFlow {
        send(RemoteResource.Loading)
        delay(3000L)
        try {
            send(RemoteResource.Success(userDao.checkUserNameExist(userName)))

        } catch (e: Exception) {
            val errorMessage = "Something went wrong: ${e.localizedMessage}"
            send(RemoteResource.Failure(errorMessage = errorMessage))
        }
    }

    fun register(userEntity: UserEntity) = channelFlow {
        try {
            val id = userDao.registerUser(userEntity)
            send(RemoteResource.Success(id))

        } catch (e: Exception) {
            val errorMessage = "Something went wrong: ${e.localizedMessage}"
            send(RemoteResource.Failure(errorMessage = errorMessage))
        }

    }

    fun login(userName: String, password: String) = channelFlow {
        send(RemoteResource.Loading)
        delay(3000L)
        try {
            val userEntity = userDao.login(userName, password)
            send(RemoteResource.Success(userEntity))

        } catch (e: Exception) {
            val errorMessage = "Something went wrong: ${e.localizedMessage}"
            send(RemoteResource.Failure(errorMessage = errorMessage))
        }

    }
}
