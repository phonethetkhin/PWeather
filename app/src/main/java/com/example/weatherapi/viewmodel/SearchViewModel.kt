package com.example.weatherapi.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapi.model.RemoteResource
import com.example.weatherapi.model.dto.response.SearchResponseModel
import com.example.weatherapi.repository.SearchRepository
import com.example.weatherapi.ui.ui_states.SearchUIStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: SearchRepository,
    private val application: Application,

    ) : ViewModel() {
    private val _uiStates = MutableStateFlow(SearchUIStates())
    val uiStates = _uiStates.asStateFlow()

    fun cityNameEmpty() {
        _uiStates.update { it.copy(cityNameEmpty = true) }
    }

    fun toggleCityName(cityName: String) {

        _uiStates.update { it.copy(cityNameEmpty = false, cityName = cityName) }
    }

    fun showDetailDialog(showDialog: Boolean, clickedItem: SearchResponseModel? = null) {
        _uiStates.update { it.copy(showAlertDialog = showDialog, clickedItem = clickedItem) }
    }

    fun getSearchList(
        query: String
    ) {
        _uiStates.update { it.copy(errorMessage = "", searchList = listOf()) }
        repository.getSearchList(query).onEach { remoteResource ->
            when (remoteResource) {
                is RemoteResource.Loading ->
                    _uiStates.update {
                        it.copy(showLoadingDialog = true)
                    }

                is RemoteResource.Success -> {
                    if (remoteResource.data.body().isNullOrEmpty()) {
                        _uiStates.update {
                            it.copy(
                                showLoadingDialog = false,
                                errorMessage = "There is no relevant data!"

                            )
                        }
                    } else {
                        _uiStates.update {
                            it.copy(
                                showLoadingDialog = false,
                                searchList = remoteResource.data.body()!!,
                                errorMessage = ""
                            )
                        }
                    }
                }

                is RemoteResource.Failure -> {
                    _uiStates.update {
                        it.copy(
                            showLoadingDialog = false,
                            errorMessage = "Something went wrong, : ${remoteResource.errorMessage}"
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)
    }
}
