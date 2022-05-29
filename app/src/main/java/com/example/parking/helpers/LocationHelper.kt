package com.example.parking.helpers

import android.content.Context
import android.location.*
import com.example.parking.R
import com.example.parking.data.firstZone
import com.example.parking.data.secondZone
import com.example.parking.data.thirdZone
import com.google.android.gms.location.LocationCallback
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class LocationHelper @Inject constructor(
    private val geocoder: Geocoder,
    private val locationManager: LocationManager,
    @ApplicationContext private val context: Context
) : LocationCallback() {
    private var address = ""
    private var zone = ""
    private var isInZone = false

    private val _locationInfo = MutableStateFlow(AddressZoneInfo("", "", false))
    val locationInfo = _locationInfo.asStateFlow()

    fun fetchLocation() {
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
        }
    }

    private val locationListener = LocationListener { location -> updateAddress(location) }

    private fun updateAddress(location: Location) {
        val lat = location.latitude
        val long = location.longitude

        val addressList = geocoder.getFromLocation(lat, long, 1)
        address = if (addressList.size > 0) {
            addressList[0].getAddressLine(0)
        } else context.getString(R.string.invalid_address)
        updateZone(address)
        locationManager.removeUpdates(locationListener)
    }

    private fun updateZone(address: String) {
        val streetNameWithNumber = address.split(",")[0]
        val streetName = streetNameWithNumber.substring(0, streetNameWithNumber.lastIndexOf(" "))

        zone = when {
            firstZone.contains(streetName) -> "1"
            secondZone.contains(streetName) -> "2"
            thirdZone.contains(streetName) -> "3"
            else -> "none"
        }
        isLocationInZone()
    }

    private fun isLocationInZone() {
        isInZone = zone == "1" || zone == "2" || zone == "3"
        updateLocationInfo()
    }

    private fun updateLocationInfo() {
        _locationInfo.value = AddressZoneInfo(address, zone, isInZone)
    }
}

data class AddressZoneInfo(val address: String, val zone: String, val isInZone: Boolean)