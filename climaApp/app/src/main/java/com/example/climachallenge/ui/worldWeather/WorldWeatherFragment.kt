package com.example.climachallenge.ui.worldWeather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import com.example.climachallenge.R
import com.example.climachallenge.retrofit.models.OpenWeatherResponse
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class WorldWeatherFragment : Fragment(), OnMapReadyCallback {

    private lateinit var worldWeatherViewModel: WorldWeatherViewModel
    private var weatherFromMap: LiveData<OpenWeatherResponse>? = null
    private lateinit var myMap: GoogleMap
    private var myMarker: Marker? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        worldWeatherViewModel =
            ViewModelProvider(this).get(WorldWeatherViewModel::class.java)

        return inflater.inflate(R.layout.fragment_world_weather, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap) {
        myMap = map
        setMapSettings()
        myMap.setOnMapClickListener { latLng ->
            if (latLng != null) {
                myMarker?.remove()
                myMarker = myMap.addMarker(
                    MarkerOptions().position(
                        LatLng(
                            latLng.latitude,
                            latLng.longitude
                        )
                    )
                )
                myMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))

                if (weatherFromMap != null) {
                    weatherFromMap =
                        worldWeatherViewModel.getWeatherDataFromMap(
                            latLng.latitude,
                            latLng.longitude
                        )
                } else {
                    weatherFromMap =
                        worldWeatherViewModel.getWeatherDataFromMap(
                            latLng.latitude,
                            latLng.longitude
                        )
                    weatherFromMap?.observe(
                        viewLifecycleOwner,
                        { it ->
                            if (it != null) {
                                WeatherMapDialogFragment(it).show(
                                    (context as AppCompatActivity).supportFragmentManager,
                                    WeatherMapDialogFragment::class.java.name
                                )
                                myMarker?.title = weatherFromMap?.value?.timezone
                            }
                        })
                }
            }
        }
    }

    private fun setMapSettings() {
        myMap.uiSettings.isZoomControlsEnabled = true
    }
}