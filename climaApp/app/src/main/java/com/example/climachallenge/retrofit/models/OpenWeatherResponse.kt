package com.example.climachallenge.retrofit.models

import com.example.climachallenge.retrofit.models.Current
import com.example.climachallenge.retrofit.models.Daily

data class OpenWeatherResponse(
    val current: Current,
    val daily: List<Daily>,
    val lat: Double,
    val lon: Double,
    val timezone: String,
    val timezone_offset: Int
)