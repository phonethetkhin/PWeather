package com.ptk.pweather.ui.ui_states

import com.ptk.pweather.model.dto.response.SearchResponseModel

data class SearchUIStates(
    val cityName: String = "",
    val cityNameEmpty: Boolean = false,
    val showLoadingDialog: Boolean = false,
    val errorMessage: String = "",
    val showAlertDialog: Boolean = false,
    val searchList: List<SearchResponseModel> = listOf(),
    val clickedItem: SearchResponseModel? = null
)