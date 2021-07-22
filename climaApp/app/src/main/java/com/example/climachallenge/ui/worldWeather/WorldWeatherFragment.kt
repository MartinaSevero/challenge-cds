package com.example.climachallenge.ui.worldWeather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
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

    override fun onMapReady(map: GoogleMap) { //FIXME
        myMap = map
        setMapSettings()
        val sydney = LatLng(-34.0, 151.0)
        myMarker =
            myMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney")) //FIXME
        myMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))

        myMap.setOnMapClickListener { latLng ->
            if (latLng != null) {
                var weatherFromMap =
                    worldWeatherViewModel.getWeatherDataFromMap(latLng.latitude, latLng.longitude)
                weatherFromMap?.observe(
                    viewLifecycleOwner, object : Observer<OpenWeatherResponse> {
                        override fun onChanged(it: OpenWeatherResponse?) {
                            if (it != null) {
                                WeatherMapDialogFragment(it).show(
                                    (context as AppCompatActivity).supportFragmentManager,
                                    WeatherMapDialogFragment::class.java.name
                                )
                            }
                            weatherFromMap.removeObserver(this)
                        }
                    })
                myMarker.remove()
                myMarker = myMap.addMarker(
                    MarkerOptions().position(
                        LatLng(
                            latLng.latitude,
                            latLng.longitude
                        )
                    ).title("Marker in Sydney") //FIXME
                )
            }
        }
    }

    private fun setMapSettings() {
        myMap.uiSettings.isZoomControlsEnabled = true
    }
}