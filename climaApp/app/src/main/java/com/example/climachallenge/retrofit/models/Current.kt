package com.example.climachallenge.retrofit.models

import com.example.climachallenge.retrofit.models.WeatherData

data class Current(
    val clouds: Int,
    val dew_point: Double,
    val dt: Int,
    val feels_like: Double,
    val humidity: Int,
    val pressure: Int,
    val sunrise: Int,
    val sunset: Int,
    val temp: Double,
    val uvi: Int,
    val visibility: Int,
    val weather: List<WeatherData>,
    val wind_deg: Int,
    val wind_speed: Double
)