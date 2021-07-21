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
import com.example.climachallenge.retrofit.models.OpenWeatherResponse
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

class WeatherMapDialogFragment(private val weatherData: OpenWeatherResponse) : DialogFragment() {

    private val currentWeather = weatherData.current

    override fun onStart() { //FIXME
        super.onStart()
        dialog?.window?.setLayout(
            ConstraintLayout.LayoutParams.MATCH_PARENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
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
        val tvWeatherDescription: TextView =
            view.findViewById(R.id.text_view_weather_map_description)
        val tvWeatherIcon: ImageView = view.findViewById(R.id.image_view_weather_map_icon)
        //FIXME indicate which data is showing for each tv
        tvTimezone.text = weatherData.timezone
        tvDate.text = formatWeatherDate(currentWeather.dt)
        tvTemperature.text = currentWeather.temp.toString()
        tvHumidity.text = currentWeather.humidity.toString()
        tvVisibility.text = currentWeather.visibility.toString()
        tvWindSpeed.text = currentWeather.wind_speed.toString()
        tvSunrise.text = currentWeather.sunrise.toString()
        tvSunset.text = currentWeather.sunset.toString()
        tvWeatherDescription.text = currentWeather.weather[0].description
        tvWeatherIcon.setImageResource(getWeatherIcon())

        return view
    }

    private fun getWeatherIcon(): Int {
        val weatherCondition = currentWeather.weather[0].main
        var icon = 0
        when (weatherCondition) {
            "Thunderstorm" -> icon = R.drawable.ic_weather_thunderstorm
            "Drizzle" -> icon = R.drawable.ic_weather_drizzle
            "Rain" -> icon = R.drawable.ic_weather_rain
            "Snow" -> icon = R.drawable.ic_weather_snow
            "Clear" -> icon = R.drawable.ic_weather_clear
            "Clouds" -> icon = R.drawable.ic_weather_clouds
            else -> { //Atmosphere
                icon = R.drawable.ic_weather_atmosphere
            }
        }

        return icon
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun formatWeatherDate(datetime: Double): String {
        val date = Date(datetime.toLong() * 1000).toInstant()
        val localDateTime = LocalDateTime.ofInstant(date, ZoneId.systemDefault())
        return localDateTime.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL))
    }

}