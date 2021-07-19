package com.example.climachallenge.ui.weatherToday

import android.os.Build
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.example.climachallenge.R
import com.example.climachallenge.retrofit.models.Daily
import com.example.climachallenge.retrofit.models.OpenWeatherResponse
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*
import kotlin.collections.ArrayList

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
                .inflate(R.layout.fragment_weather_today, parent, false)
        return ViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dailyWeatherList[position]
        holder.tvWeatherData.text = item.toString()
        val weatherDate = formatWeatherDate(item)
        holder.tvWeatherDay.text = weatherDate
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun formatWeatherDate(item: Daily): String {
        val date = Date(item.dt.toLong() * 1000).toInstant()
        val localDateTime = LocalDateTime.ofInstant(date, ZoneId.systemDefault())
        return localDateTime.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL))
    }

    override fun getItemCount(): Int = dailyWeatherList.size

    fun setData(weatherDailyData: List<Daily>?) {
        dailyWeatherList = weatherDailyData!!
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvWeatherData: TextView = view.findViewById(R.id.text_view_weather_data)
        val tvWeatherDay: TextView = view.findViewById(R.id.text_view_weather_day)
    }
}