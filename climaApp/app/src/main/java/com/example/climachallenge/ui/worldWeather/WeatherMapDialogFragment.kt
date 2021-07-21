package com.example.climachallenge.ui.worldWeather

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.DialogFragment
import com.example.climachallenge.R
import com.example.climachallenge.retrofit.models.Daily
import com.example.climachallenge.retrofit.models.OpenWeatherResponse
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

//FIXME setear datos para mostrar
class WeatherMapDialogFragment(private val weatherData: OpenWeatherResponse) : DialogFragment() {

    private val currentWeather = weatherData.current
    private val currentWeatherTimezone = weatherData.timezone

    override fun onStart() { //FIXME
        super.onStart()
        dialog?.window?.setLayout(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_weather_map_dialog, container, false)

        val tvTimezone: TextView = view.findViewById(R.id.text_view_weather_map_timezone)
        val tvDate: TextView = view.findViewById(R.id.text_view_weather_map_date)
        val tvTemperature: TextView = view.findViewById(R.id.text_view_weather_map_temp)
        val tvHumidity: TextView = view.findViewById(R.id.text_view_weather_map_humidity)
        val tvVisibility: TextView = view.findViewById(R.id.text_view_weather_map_visibility)
        val tvWindSpeed: TextView = view.findViewById(R.id.text_view_weather_map_wind_speed)
        val tvSunrise: TextView = view.findViewById(R.id.text_view_weather_map_sunrise)
        val tvSunset: TextView = view.findViewById(R.id.text_view_weather_map_sunset)
        val tvWeatherDescription: TextView = view.findViewById(R.id.text_view_weather_map_description)
        val tvWeatherIcon: ImageView = view.findViewById(R.id.image_view_weather_map_icon)
        val tvWeatherDescriptionIcon: ImageView = view.findViewById(R.id.image_view_weather_map_description_icon)

        return view
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun formatWeatherDate(datetime: Double): String {
        val date = Date(datetime.toLong() * 1000).toInstant()
        val localDateTime = LocalDateTime.ofInstant(date, ZoneId.systemDefault())
        return localDateTime.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL))
    }

}