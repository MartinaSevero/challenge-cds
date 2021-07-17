package com.example.climachallenge.ui.weatherToday

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.climachallenge.R
import com.example.climachallenge.retrofit.models.OpenWeatherResponse

class WeatherTodayFragment : Fragment() {
    private var weatherTodayViewModel: WeatherTodayViewModel? = null
    private var weatherTodayAdapter: WeatherTodayRecyclerViewAdapter? = null
    private var weatherData: OpenWeatherResponse? = null

    private var columnCount = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }

        weatherTodayViewModel?.assignWeatherData(-34.90, -56.17)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_weather_today_list, container, false)

        // Obtain ViewModel
        weatherTodayViewModel = ViewModelProvider(this).get(WeatherTodayViewModel::class.java)

        weatherTodayAdapter = WeatherTodayRecyclerViewAdapter()

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = weatherTodayAdapter
            }
        }

        // Weather data observer FIXME
        weatherTodayViewModel!!.getWeatherData()?.observe(viewLifecycleOwner, Observer {
            if (weatherData != null) {
                weatherData = it
                weatherTodayAdapter!!.setData(weatherData?.daily)
            }
        })

        return view
    }

    companion object {
        const val ARG_COLUMN_COUNT = "column-count"

        @JvmStatic
        fun newInstance(columnCount: Int) =
                WeatherTodayFragment().apply {
                    arguments = Bundle().apply {
                        putInt(ARG_COLUMN_COUNT, columnCount)
                    }
                }
    }
}