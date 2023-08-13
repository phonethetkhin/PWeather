package com.example.weatherapi.ui.ui_states

import com.example.weatherapi.model.dto.response.SearchResponseModel

data class SearchUIStates(
    val cityName: String = "",
    val cityNameEmpty: Boolean = false,
    val showLoadingDialog: Boolean = false,
    val errorMessage: String = "",
    val showAlertDialog: Boolean = false,
    val searchList: List<SearchResponseModel> = listOf(),
    val clickedItem: SearchResponseModel? = null
)