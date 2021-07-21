package com.example.climachallenge.ui.weatherToday

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.DialogInterface
import android.content.Intent
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.climachallenge.MainActivity
import com.example.climachallenge.R
import com.example.climachallenge.common.MyApp
import com.example.climachallenge.retrofit.models.OpenWeatherResponse
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.CompositePermissionListener
import com.karumi.dexter.listener.single.DialogOnDeniedPermissionListener
import com.karumi.dexter.listener.single.PermissionListener

class WeatherTodayFragment : Fragment(), PermissionListener {
    private var weatherTodayViewModel: WeatherTodayViewModel? = null
    private var weatherTodayAdapter: WeatherTodayRecyclerViewAdapter? = null
    private var weatherData: OpenWeatherResponse? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private var columnCount = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        checkPermissions()

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

    @SuppressLint("MissingPermission")
    override fun onPermissionGranted(response: PermissionGrantedResponse?) {
        if (isGpsEnabled()) {
            fusedLocationClient.getCurrentLocation(
                LocationRequest.PRIORITY_HIGH_ACCURACY,
                null
            ).addOnSuccessListener { location ->
                if (location != null) {
                    setAdapterData(location.latitude, location.longitude)
                } else {
                    Toast.makeText(
                        MyApp.instance,
                        MyApp.instance.getString(R.string.location_error),
                        Toast.LENGTH_LONG
                    ).show()
                    setAdapterData()
                }
            }.addOnFailureListener {
                Toast.makeText(
                    MyApp.instance,
                    MyApp.instance.getString(R.string.location_error),
                    Toast.LENGTH_LONG
                ).show()
            }
        } else {
            Toast.makeText(
                MyApp.instance,
                MyApp.instance.getString(R.string.gps_error),
                Toast.LENGTH_LONG
            ).show()
            setAdapterData()
        }

    }

    override fun onPermissionDenied(response: PermissionDeniedResponse?) {
        Log.i("weathertodayfragment", "Allow location permissions to show weather data")
        setAdapterData()
    }

    override fun onPermissionRationaleShouldBeShown(
        permission: PermissionRequest?,
        token: PermissionToken?
    ) {
        Log.i("weathertodayfragment", "Turn on GPS to get current location")
    }

    private fun checkPermissions() {
        val dialogOnDeniedPermissionListener: PermissionListener =
            DialogOnDeniedPermissionListener.Builder.withContext(
                activity
            )
                .withTitle(R.string.denied_location_title)
                .withMessage(R.string.denied_location_message)
                .withButtonText(R.string.denied_location_button)
                .withIcon(R.mipmap.ic_launcher)
                .build()

        var allPermissionsListener: PermissionListener = CompositePermissionListener(
            this,
            dialogOnDeniedPermissionListener
        )
        Dexter.withActivity(activity)
            .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
            .withListener(allPermissionsListener)
            .check()
    }

    private fun setAdapterData(latitude: Double = -34.90, longitude: Double = -56.17) {
        // Weather data observer
        weatherTodayViewModel!!.getWeatherData(latitude, longitude)?.observe(
            viewLifecycleOwner,
            Observer {
                if (it != null) {
                    weatherData = it
                    weatherTodayAdapter!!.setData(weatherData?.daily)
                    (requireActivity() as MainActivity).supportActionBar?.title =
                        resources.getString(
                            R.string.title_today
                        ) + " - " + weatherData!!.timezone
                }
            })
    }

    private fun isGpsEnabled(): Boolean {
        var gpsEnabled = false
        val locationManager =
            requireContext().getSystemService(LOCATION_SERVICE) as LocationManager
        try {
            gpsEnabled =
                locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) //GPS is enabled
        } catch (ex: Exception) {
        }
        return gpsEnabled
    }
}