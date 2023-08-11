package com.example.weatherapi.repository

import android.app.Application
import com.example.weatherapi.model.RemoteResource
import com.example.weatherapi.network.APIService
import com.example.weatherapi.util.Constants
import com.ptk.pWeather.R
import kotlinx.coroutines.flow.channelFlow
import org.apache.http.conn.ConnectTimeoutException
import java.net.SocketTimeoutException
import javax.inject.Inject

class SearchRepository @Inject constructor(
    private val application: Application,
    private val apiService: APIService,
) {
    fun getSearchList(
        query: String
    ) = channelFlow {
        send(RemoteResource.Loading)
        try {
            val response =
                apiService.getSearchList(Constants.API_KEY, query)
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