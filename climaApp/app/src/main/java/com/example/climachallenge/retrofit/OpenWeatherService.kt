package com.example.climachallenge.retrofit

import com.example.climachallenge.retrofit.models.OpenWeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherService {
    @GET("onecall")
    fun getWeatherData(@Query("lat") lat: Double,
                       @Query("lon") lon: Double,
                       @Query("exclude") exclude: String?,
                       @Query("units") units: String?): Call<OpenWeatherResponse>
}
