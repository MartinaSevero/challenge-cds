package com.example.climachallenge.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DashboardViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Clima segun ubicacion en mapa"
    }
    val text: LiveData<String> = _text
}