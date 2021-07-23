package com.example.climachallenge.ui.weatherToday

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.climachallenge.R
import com.example.climachallenge.retrofit.models.Daily
import com.example.climachallenge.retrofit.models.OpenWeatherResponse
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.roundToInt

class WeatherTodayRecyclerViewAdapter
    : RecyclerView.Adapter<WeatherTodayRecyclerViewAdapter.ViewHolder>() {

    private val wOnClickListener: View.OnClickListener
    private var dailyWeatherList: List<Daily> = ArrayList()

    init {
        wOnClickListener = View.OnClickListener { v ->
            v.tag as OpenWeatherResponse
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_weather_today_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dailyWeatherList[position]
        val date = formatWeatherDate(item)
        holder.tvWeatherDate.text = date
        holder.tvWeatherTemp.text = item.temp.day.roundToInt().toString() + "º"
        holder.tvWeatherTempMin.text = holder.itemView.context.getString(
            R.string.weather_temp_min,
            item.temp.min.roundToInt().toString() + "º"
        )
        holder.tvWeatherTempMax.text = holder.itemView.context.getString(
            R.string.weather_temp_max,
            item.temp.max.roundToInt().toString() + "º"
        )
        holder.tvWeatherHumidity.text = holder.itemView.context.getString(
            R.string.weather_humidity,
            item.humidity.toString() + " %"
        )
        val windSpeed = (item.wind_speed * 3.6).roundToInt()
        holder.tvWeatherWindSpeed.text = holder.itemView.context.getString(
            R.string.weather_wind_speed,
            "$windSpeed km/h"
        )
        holder.tvWeatherSunrise.text = holder.itemView.context.getString(
            R.string.weather_sunrise,
            formatSunData(item.sunrise)
        )
        holder.tvWeatherSunset.text = holder.itemView.context.getString(
            R.string.weather_sunset,
            formatSunData(item.sunset)
        )
        holder.tvWeatherDescription.text = item.weather[0].description
        holder.tvWeatherIcon.setImageResource(getWeatherIcon(item))
    }

    private fun getWeatherIcon(item: Daily): Int {
        return when (item.weather[0].main) {
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

    private fun formatWeatherDate(item: Daily): String {
        var dateToReturn = ""
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val date = Date(item.dt.toLong() * 1000).toInstant()
            val localDateTime = LocalDateTime.ofInstant(date, ZoneId.systemDefault())
            dateToReturn = localDateTime.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL))
        } else {
            return try {
                val simpleDate = SimpleDateFormat("dd/MM/yyyy")
                val date = Date(item.dt.toLong() * 1000)

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

    override fun getItemCount(): Int = dailyWeatherList.size

    fun setData(weatherDailyData: List<Daily>?) {
        dailyWeatherList = weatherDailyData!!
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvWeatherDate: TextView = view.findViewById(R.id.text_view_weather_date)
        val tvWeatherTemp: TextView = view.findViewById(R.id.text_view_weather_temp)
        val tvWeatherTempMin: TextView = view.findViewById(R.id.text_view_weather_temp_min)
        val tvWeatherTempMax: TextView = view.findViewById(R.id.text_view_weather_temp_max)
        val tvWeatherHumidity: TextView = view.findViewById(R.id.text_view_weather_humidity)
        val tvWeatherWindSpeed: TextView = view.findViewById(R.id.text_view_weather_wind_speed)
        val tvWeatherSunrise: TextView = view.findViewById(R.id.text_view_weather_sunrise)
        val tvWeatherSunset: TextView = view.findViewById(R.id.text_view_weather_sunset)
        val tvWeatherDescription: TextView = view.findViewById(R.id.text_view_weather_description)
        val tvWeatherIcon: ImageView = view.findViewById(R.id.image_view_weather_icon)
    }
}