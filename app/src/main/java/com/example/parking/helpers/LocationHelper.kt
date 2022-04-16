package com.example.parking.helpers

import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import androidx.lifecycle.MutableLiveData
import com.example.parking.data.firstZone
import com.example.parking.data.secondZone
import com.example.parking.data.thirdZone
import javax.inject.Inject

class LocationHelper @Inject constructor(
    private val geocoder: Geocoder
) {
    var mutableAddress = MutableLiveData("Fetching location")
    var mutableZone = MutableLiveData("Fetching zone")
    var mutableIsLocationInZone = MutableLiveData(false)

    val locationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            updateAddress(location)
        }
    }

    private fun updateAddress(location: Location) {
        val lat = location.latitude
        val long = location.longitude

        val addressList = geocoder.getFromLocation(lat, long, 1)
        mutableAddress.value = if (addressList.size > 0) {
            addressList[0].getAddressLine(0)
        } else "Address couldn't be found"
        updateZone(mutableAddress.value!!)
    }

    private fun updateZone(address: String) {
        val streetNameWithNumber = address.split(",")[0]
        val streetName = streetNameWithNumber.substring(0, streetNameWithNumber.lastIndexOf(" "))

        mutableZone.value = when {
            firstZone.contains(streetName) -> "1"
            secondZone.contains(streetName) -> "2"
            thirdZone.contains(streetName) -> "3"
            else -> "none"
        }
        isLocationInZone()
    }

    private fun isLocationInZone() {
        mutableIsLocationInZone.value = mutableZone.value == "1" ||
                mutableZone.value == "2" ||
                mutableZone.value == "3"
    }
}