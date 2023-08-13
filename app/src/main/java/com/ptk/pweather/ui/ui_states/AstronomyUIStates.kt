package com.ptk.pweather.ui.ui_states

import com.ptk.pweather.model.dto.response.AstronomyResponseModel

data class AstronomyUIStates(
    val cityName: String = "",
    val cityNameEmpty: Boolean = false,

    val dateName: String = "",
    val dateEmpty: Boolean = false,

    val showLoadingDialog: Boolean = false,
    val errorMessage: String = "",
    val showAlertDialog: Boolean = false,

    val astronomyResponse: AstronomyResponseModel? = null,
)