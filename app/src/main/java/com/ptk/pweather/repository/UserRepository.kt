package com.ptk.pweather.repository

import android.app.Application
import com.ptk.pweather.model.RemoteResource
import com.ptk.pweather.network.APIService
import com.ptk.pweather.roomdb.dao.UserDao
import kotlinx.coroutines.flow.channelFlow
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val application: Application,
    private val apiService: APIService,
    private val userDao: UserDao

) {

    fun register(userName: String, password: String) = channelFlow {
        send(RemoteResource.Loading)
        try {
            val id = userDao.registerUser(userName, password)
            send(RemoteResource.Success(id))

        } catch (e: Exception) {
            val errorMessage = "Something went wrong: ${e.localizedMessage}"
            send(RemoteResource.Failure(errorMessage = errorMessage))
        }

    }

    fun login(userName: String, password: String) = channelFlow {
        send(RemoteResource.Loading)
        try {
            val userEntity = userDao.login(userName, password)
            if (userEntity != null) {
                send(RemoteResource.Success(userEntity))
            } else {
                send(RemoteResource.Failure(errorMessage = "Username or password incorrect"))
            }

        } catch (e: Exception) {
            val errorMessage = "Something went wrong: ${e.localizedMessage}"
            send(RemoteResource.Failure(errorMessage = errorMessage))
        }

    }
}
