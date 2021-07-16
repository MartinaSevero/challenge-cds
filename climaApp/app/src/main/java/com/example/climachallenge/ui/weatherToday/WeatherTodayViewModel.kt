package com.example.climachallenge.ui.weatherToday

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.climachallenge.repository.OpenWeatherRepository
import com.example.climachallenge.retrofit.models.OpenWeatherResponse

class WeatherTodayViewModel: ViewModel() {
    private var openWeatherRepository: OpenWeatherRepository
    private var weatherData: LiveData<OpenWeatherResponse>

    init {
        openWeatherRepository = OpenWeatherRepository()
        weatherData = openWeatherRepository?.weatherData()!!
    }

    fun getWeatherData(): LiveData<OpenWeatherResponse> {
        return weatherData
    }
}