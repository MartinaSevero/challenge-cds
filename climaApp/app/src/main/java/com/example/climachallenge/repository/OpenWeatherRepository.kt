package com.example.climachallenge.repository

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.climachallenge.common.MyApp
import com.example.climachallenge.retrofit.OpenWeatherClient
import com.example.climachallenge.retrofit.OpenWeatherService
import com.example.climachallenge.retrofit.models.OpenWeatherResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OpenWeatherRepository {
    var openWeatherService: OpenWeatherService? = null
    var openWeatherClient: OpenWeatherClient? = null
    var weatherData: MutableLiveData<OpenWeatherResponse>? = null

    init {
        openWeatherClient = OpenWeatherClient.instance
        openWeatherService = openWeatherClient?.getOpenWeatherService()
        weatherData = weatherData()
    }

    fun weatherData(): MutableLiveData<OpenWeatherResponse>? {
        val call: Call<OpenWeatherResponse>? = openWeatherService?.getWeatherData()
        call?.enqueue(object: Callback<OpenWeatherResponse> {
            override fun onResponse(call: Call<OpenWeatherResponse>, response: Response<OpenWeatherResponse>) {
                if (response.isSuccessful) {
                    weatherData?.value = response.body()
                }
            }

            override fun onFailure(call: Call<OpenWeatherResponse>, t: Throwable) {
                Toast.makeText(MyApp.instance, "Error in the call", Toast.LENGTH_LONG).show()
            }

        })

        return weatherData
    }
}