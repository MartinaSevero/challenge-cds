package com.example.climachallenge.retrofit.models

data class OpenWeatherResponse(
    val current: Current,
    val daily: List<Daily>?,
    val lat: Double,
    val lon: Double,
    val timezone: String,
    val timezone_offset: Double
)