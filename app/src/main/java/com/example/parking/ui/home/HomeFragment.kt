package com.example.parking.ui.home

import android.Manifest
import android.content.Context
import android.location.*
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.parking.R
import com.example.parking.Utils.hasPermission
import com.example.parking.data.*
import com.example.parking.databinding.FragmentHomeBinding
import java.util.*

class HomeFragment : Fragment(R.layout.fragment_home) {

    private val geocoder by lazy { Geocoder(requireContext(), Locale.getDefault()) }
    private lateinit var binding: FragmentHomeBinding
    private val locationPermission = Manifest.permission.ACCESS_FINE_LOCATION
    private lateinit var locationManager: LocationManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentHomeBinding.bind(view)

        locationManager =
            requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        trackLocation()
    }

    private val locationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            updateLocationDisplay(location)
        }
    }

    private fun updateLocationDisplay(location: Location) {
        val lat = location.latitude
        val long = location.longitude
        var address = ""

        val addressList = geocoder.getFromLocation(lat, long, 1)
        if (addressList.size > 0) {
            address = addressList[0].getAddressLine(0)
            "CURRENT LOCATION: $address".also { binding.tvAddress.text = it }
        } else Toast.makeText(requireContext(), "Address not found", Toast.LENGTH_SHORT).show()
        updateZoneDisplay(address)
    }

    private fun updateZoneDisplay(address: String) {
        val streetName = address.split(",")[0].filter { it.isLetter() }

        val zone = when{
            firstZone.contains(streetName) -> 1
            secondZone.contains(streetName) -> 2
            thirdZone.contains(streetName) -> 3
            else -> "None"
        }

        "CURRENT ZONE: $zone".also { binding.tvZone.text = it }
    }

    private fun trackLocation() {
        if (hasPermission(locationPermission)) {
            startTrackingLocation()
        } else {
            permReqLauncher.launch(arrayOf(locationPermission))
        }
    }

    private fun startTrackingLocation() {
        val criteria = Criteria()
        criteria.accuracy = Criteria.ACCURACY_FINE

        val provider = locationManager.getBestProvider(criteria, true)
        val minTime = 1000L
        val minDistance = 1.0f
        try {
            locationManager.requestLocationUpdates(
                provider!!,
                minTime,
                minDistance,
                locationListener
            )
        } catch (e: SecurityException) {
            Toast.makeText(requireContext(), "Permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    private val permReqLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permission ->
            val granted = permission.entries.all {
                it.value == true
            }
            if (granted) {
                startTrackingLocation()
            } else Toast.makeText(requireContext(), "Permission denied", Toast.LENGTH_SHORT).show()
        }



    override fun onPause() {
        super.onPause()
        locationManager.removeUpdates(locationListener)
    }

}