package com.ptk.pweather.repository

import android.app.Application
import com.ptk.pweather.R
import com.ptk.pweather.model.RemoteResource
import com.ptk.pweather.network.APIService
import com.ptk.pweather.util.Constants
import kotlinx.coroutines.flow.channelFlow
import org.apache.http.conn.ConnectTimeoutException
import java.net.SocketTimeoutException
import javax.inject.Inject

class AstronomyRepository @Inject constructor(
    private val application: Application,
    private val apiService: APIService,
) {
    fun getAstronomy(
        query: String,
        dateTime: String,
    ) = channelFlow {
        send(RemoteResource.Loading)
        try {
            val response =
                apiService.getAstronomy(Constants.API_KEY, query, dateTime)
            send(RemoteResource.Success(response))
        } catch (e: Exception) {
            when (e) {
                is SocketTimeoutException -> {
                    send(RemoteResource.Failure(errorMessage = application.getString(R.string.connection_error_message)))
                }

                is ConnectTimeoutException -> {
                    send(RemoteResource.Failure(errorMessage = application.getString(R.string.connection_error_message)))
                }

                else -> {
                    val errorMessage = "Something went wrong: ${e.localizedMessage}"
                    send(RemoteResource.Failure(errorMessage = errorMessage))
                }
            }
        }
    }
}
