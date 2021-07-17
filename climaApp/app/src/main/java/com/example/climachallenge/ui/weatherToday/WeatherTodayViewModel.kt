package com.example.climachallenge.ui.weatherToday

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.climachallenge.repository.OpenWeatherRepository
import com.example.climachallenge.retrofit.models.OpenWeatherResponse

class WeatherTodayViewModel: ViewModel() {
    private var openWeatherRepository: OpenWeatherRepository
    private var weatherData: LiveData<OpenWeatherResponse>? = null

    init {
        openWeatherRepository = OpenWeatherRepository()
        //weatherData = openWeatherRepository?.obtainWeatherData(34.2,36.5)!! FIXME
    }

    fun assignWeatherData(lat: Double, lon: Double) {
        weatherData = openWeatherRepository.obtainWeatherData(lat, lon)!!
    }

    fun getWeatherData(): LiveData<OpenWeatherResponse>? {
        return weatherData
    }
}