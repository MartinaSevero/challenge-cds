package com.example.climachallenge.retrofit

import com.example.climachallenge.retrofit.models.OpenWeatherResponse
import retrofit2.Call
import retrofit2.http.GET

interface OpenWeatherService {
    @GET("onecall")
    //@GET("onecall?lat=-34.90&lon=-56.17&exclude=hourly,minutely&units=metric")
    fun getWeatherData(): Call<OpenWeatherResponse>
}