package com.example.climachallenge.retrofit.models

data class Daily(
    val clouds: Double,
    val dew_point: Double,
    val dt: Double,
    val feels_like: FeelsLike,
    val humidity: Double,
    val moon_phase: Double,
    val moonrise: Double,
    val moonset: Double,
    val pop: Double,
    val pressure: Double,
    val sunrise: Double,
    val sunset: Double,
    val temp: Temperature,
    val uvi: Double,
    val weather: List<WeatherData>,
    val wind_deg: Double,
    val wind_gust: Double,
    val wind_speed: Double
)