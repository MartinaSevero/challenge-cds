package com.example.climachallenge.ui.worldWeather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.climachallenge.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class WorldWeatherFragment : Fragment(), OnMapReadyCallback {

    private lateinit var worldWeatherViewModel: WorldWeatherViewModel
    private lateinit var myMap: GoogleMap
    private lateinit var myMarker: Marker

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
        val sydney = LatLng(-34.0, 151.0)
        myMarker = map.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        map.moveCamera(CameraUpdateFactory.newLatLng(sydney))

        myMap.setOnMapClickListener { latLng ->
            if (latLng != null) {
                myMarker.remove()
                myMarker = map.addMarker(
                    MarkerOptions().position(
                        LatLng(
                            latLng.latitude,
                            latLng.longitude
                        )
                    ).title("Marker in Sydney")
                )
            }
        }
    }

    private fun setMapSettings() {
        myMap.uiSettings.isZoomControlsEnabled = true
    }
}