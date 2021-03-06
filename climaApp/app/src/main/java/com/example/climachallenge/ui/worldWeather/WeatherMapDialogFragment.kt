package com.example.climachallenge.ui.worldWeather

import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.DialogFragment
import com.example.climachallenge.R
import com.example.climachallenge.retrofit.models.OpenWeatherResponse
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*
import kotlin.math.roundToInt

class WeatherMapDialogFragment(private val weatherData: OpenWeatherResponse) : DialogFragment() {

    private val currentWeather = weatherData.current

    override fun onResume() {
        super.onResume()
        dialog?.window?.setLayout(
            ConstraintLayout.LayoutParams.MATCH_PARENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onDestroyView() {
        val dialog: Dialog? = dialog
        if (dialog != null && retainInstance) {
            dialog.setDismissMessage(null)
        }
        super.onDestroyView()
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
        val tvWindSpeed: TextView = view.findViewById(R.id.text_view_weather_map_wind_speed)
        val tvSunrise: TextView = view.findViewById(R.id.text_view_weather_map_sunrise)
        val tvSunset: TextView = view.findViewById(R.id.text_view_weather_map_sunset)
        val tvWeatherDescription: TextView =
            view.findViewById(R.id.text_view_weather_map_description)
        val tvWeatherIcon: ImageView = view.findViewById(R.id.image_view_weather_map_icon)

        tvTimezone.text = getString(R.string.weather_map_timezone, weatherData.timezone)
        tvDate.text = formatWeatherDate(currentWeather.dt)
        tvTemperature.text = currentWeather.temp.roundToInt().toString() + "??"
        tvHumidity.text = getString(
            R.string.weather_humidity,
            currentWeather.humidity.roundToInt().toString() + " %"
        )
        val windSpeed = (currentWeather.wind_speed * 3.6).roundToInt()
        tvWindSpeed.text = getString(R.string.weather_wind_speed, "$windSpeed km/h")
        tvSunrise.text = getString(R.string.weather_sunrise, formatSunData(currentWeather.sunrise))
        tvSunset.text = getString(R.string.weather_sunset, formatSunData(currentWeather.sunset))
        tvWeatherDescription.text = currentWeather.weather[0].description
        tvWeatherIcon.setImageResource(getWeatherIcon())

        retainInstance = true
        view.setOnClickListener {
            dialog?.dismiss()
        }

        return view
    }

    private fun getWeatherIcon(): Int {
        return when (currentWeather.weather[0].main) {
            "Thunderstorm" -> R.drawable.ic_weather_thunderstorm
            "Drizzle" -> R.drawable.ic_weather_drizzle
            "Rain" -> R.drawable.ic_weather_rain
            "Snow" -> R.drawable.ic_weather_snow
            "Clear" -> R.drawable.ic_weather_clear
            "Clouds" -> R.drawable.ic_weather_clouds
            else -> { //Atmosphere
                R.drawable.ic_weather_atmosphere
            }
        }
    }

    private fun formatWeatherDate(datetime: Double): String {
        var dateToReturn = ""
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val date = Date(datetime.toLong() * 1000).toInstant()
            val localDateTime = LocalDateTime.ofInstant(date, ZoneId.systemDefault())
            dateToReturn = localDateTime.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL))
        } else {
            return try {
                val simpleDate = SimpleDateFormat("dd/MM/yyyy")
                val date = Date(datetime.toLong() * 1000)
                simpleDate.format(date)
            } catch (e: Exception) {
                e.toString()
            }
        }
        return dateToReturn
    }

    private fun formatSunData(datetime: Double): String {
        var dateToReturn = ""
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val date = Date(datetime.toLong() * 1000).toInstant()
            val localDateTime = LocalDateTime.ofInstant(date, ZoneId.systemDefault())
            dateToReturn = localDateTime.format(DateTimeFormatter.ofPattern("HH:mm"))
        } else {
            return try {
                val simpleDate = SimpleDateFormat("HH:mm")
                val date = Date(datetime.toLong() * 1000)

                simpleDate.format(date)
            } catch (e: Exception) {
                e.toString()
            }
        }
        return dateToReturn
    }

}