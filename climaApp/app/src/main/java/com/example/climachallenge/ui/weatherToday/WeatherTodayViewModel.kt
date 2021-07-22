package com.example.climachallenge.ui.weatherToday

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.climachallenge.repository.OpenWeatherRepository
import com.example.climachallenge.retrofit.models.OpenWeatherResponse

class WeatherTodayViewModel : ViewModel() {
    private var openWeatherRepository: OpenWeatherRepository
    private var weatherData: LiveData<OpenWeatherResponse>? = null

    init {
        openWeatherRepository = OpenWeatherRepository()
    }

    fun getWeatherData(lat: Double, lon: Double): LiveData<OpenWeatherResponse>? {
        weatherData = openWeatherRepository.obtainWeatherData(lat, lon)
        return weatherData
    }
}