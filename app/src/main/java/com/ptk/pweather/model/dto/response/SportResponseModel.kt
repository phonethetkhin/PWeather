package com.ptk.pweather.model.dto.response

data class SportResponseModel(
    val golf: List<SportResponseItem> = listOf(),
    val cricket: List<SportResponseItem> = listOf(),
    val football: List<SportResponseItem> = listOf()
)

data class SportResponseItem(
    val country: String? = null,
    val start: String? = null,
    val match: String? = null,
    val stadium: String? = null,
    val tournament: String? = null,
    val region: String? = null
)

