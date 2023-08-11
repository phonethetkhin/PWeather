package com.example.weatherapi.ui.ui_states

import com.example.weatherapi.model.dto.response.SearchResponseModel

data class SearchUIStates(
    val showLoadingDialog: Boolean = false,
    val showAlertDialog: Boolean = false,
    val searchList: List<SearchResponseModel> = listOf()
)