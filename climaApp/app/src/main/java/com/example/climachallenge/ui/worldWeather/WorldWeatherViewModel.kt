package com.example.climachallenge.ui.worldWeather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.climachallenge.repository.OpenWeatherRepository
import com.example.climachallenge.retrofit.models.OpenWeatherResponse

class WorldWeatherViewModel : ViewModel() {
    private var openWeatherRepository: OpenWeatherRepository
    private var weatherData: LiveData<OpenWeatherResponse>? = null

    init {
        openWeatherRepository = OpenWeatherRepository()
    }

    fun getWeatherDataFromMap(lat: Double, lon: Double): LiveData<OpenWeatherResponse>? {
        weatherData = openWeatherRepository.obtainWeatherDataFromMap(lat, lon)
        return weatherData
    }
}