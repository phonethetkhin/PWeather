package com.ptk.pweather.model.dto.response

data class SearchResponseModel(
    val country: String? = null,
    val name: String? = null,
    val lon: Double? = null,
    val id: Int? = null,
    val region: String? = null,
    val lat: Double? = null,
    val url: String? = null
)
