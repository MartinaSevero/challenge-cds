package com.example.climachallenge.retrofit.models

data class WeatherData(
    val description: String,
    val icon: String,
    val id: Double,
    val main: String
)