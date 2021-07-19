package com.example.climachallenge.ui.worldWeather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WorldWeatherViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Clima segun ubicacion en mapa"
    }
    val text: LiveData<String> = _text
}