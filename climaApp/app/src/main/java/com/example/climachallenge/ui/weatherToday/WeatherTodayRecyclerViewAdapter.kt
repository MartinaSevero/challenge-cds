package com.example.climachallenge.ui.weatherToday

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.climachallenge.R
import com.example.climachallenge.retrofit.models.Daily
import com.example.climachallenge.retrofit.models.OpenWeatherResponse

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

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dailyWeatherList[position]
        holder.tvWeatherData.text = item.toString()
        //holder.contentView.text = item.content
    }

    override fun getItemCount(): Int = dailyWeatherList.size

    fun setData(weatherDailyData: List<Daily>?) {
        dailyWeatherList = weatherDailyData!!
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvWeatherData: TextView = view.findViewById(R.id.text_view_weather_data)
        //val contentView: TextView = view.findViewById(R.id.content)

        override fun toString(): String {
            return super.toString() + " '" + tvWeatherData.text + "'"
        }
    }
}