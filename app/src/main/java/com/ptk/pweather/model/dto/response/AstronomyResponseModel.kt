package com.ptk.pweather.model.dto.response

data class AstronomyResponseModel(
    val location: Location? = null,
    val astronomy: Astronomy? = null
)

data class Location(
    val localtime: String? = null,
    val country: String? = null,
    val localtimeEpoch: Int? = null,
    val name: String? = null,
    val lon: Double? = null,
    val region: String? = null,
    val lat: Double? = null,
    val tzId: String? = null
)

data class Astronomy(
    val astro: Astro? = null
)

data class Astro(
    val moonset: String? = null,
    val moonIllumination: String? = null,
    val sunrise: String? = null,
    val moonPhase: String? = null,
    val sunset: String? = null,
    val isMoonUp: Int? = null,
    val isSunUp: Int? = null,
    val moonrise: String? = null
)

