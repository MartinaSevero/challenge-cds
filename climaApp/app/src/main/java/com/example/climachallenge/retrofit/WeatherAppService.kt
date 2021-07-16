package com.example.climachallenge.retrofit

import com.example.climachallenge.retrofit.models.OpenWeatherResponse
import retrofit2.Call
import retrofit2.http.GET

interface WeatherAppService {
    @GET("onecall?lat=-34.90&lon=-56.17&exclude=hourly,minutely&units=metric&appid=0ef07f6b9a710e651a88f0de691fbdb3")
    fun getWeatherData(): Call<OpenWeatherResponse>
}