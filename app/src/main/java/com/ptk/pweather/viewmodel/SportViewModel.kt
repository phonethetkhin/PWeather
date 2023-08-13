package com.ptk.pweather.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ptk.pweather.model.RemoteResource
import com.ptk.pweather.model.dto.response.SportResponseItem
import com.ptk.pweather.repository.SportRepository
import com.ptk.pweather.ui.ui_states.SportUIStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SportViewModel @Inject constructor(
    private val repository: SportRepository,
    private val application: Application,

    ) : ViewModel() {
    private val _uiStates = MutableStateFlow(SportUIStates())
    val uiStates = _uiStates.asStateFlow()

    fun cityNameEmpty() {
        _uiStates.update { it.copy(cityNameEmpty = true) }
    }

    fun toggleCityName(cityName: String) {

        _uiStates.update { it.copy(cityNameEmpty = false, cityName = cityName) }
    }

    fun showDetailDialog(showDialog: Boolean, clickedItem: SportResponseItem? = null) {
        _uiStates.update { it.copy(showAlertDialog = showDialog, clickedItem = clickedItem) }
    }

    fun getSportList(
        query: String
    ) {
        _uiStates.update { it.copy(errorMessage = "", sportResponseModel = null) }
        repository.getSportList(query).onEach { remoteResource ->
            when (remoteResource) {
                is RemoteResource.Loading ->
                    _uiStates.update {
                        it.copy(showLoadingDialog = true)
                    }

                is RemoteResource.Success -> {
                    if (remoteResource.data.body() == null) {
                        _uiStates.update {
                            it.copy(
                                showLoadingDialog = false,
                                errorMessage = "No matching location found."

                            )
                        }
                    } else {
                        _uiStates.update {
                            it.copy(
                                showLoadingDialog = false,
                                sportResponseModel = remoteResource.data.body()!!,
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

                else -> {}
            }
        }.launchIn(viewModelScope)
    }
}
