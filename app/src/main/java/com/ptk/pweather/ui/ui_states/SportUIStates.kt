package com.ptk.pweather.ui.ui_states

import com.ptk.pweather.model.dto.response.SportResponseItem
import com.ptk.pweather.model.dto.response.SportResponseModel

data class SportUIStates(
    val cityName: String = "",
    val cityNameEmpty: Boolean = false,
    val showLoadingDialog: Boolean = false,
    val errorMessage: String = "",
    val showAlertDialog: Boolean = false,
    val sportResponseModel: SportResponseModel? = null,
    val clickedItem: SportResponseItem? = null
)