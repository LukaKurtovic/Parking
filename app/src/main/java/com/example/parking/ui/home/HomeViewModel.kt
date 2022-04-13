package com.example.parking.ui.home

import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.parking.data.firstZone
import com.example.parking.data.secondZone
import com.example.parking.data.thirdZone
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val geocoder: Geocoder
) : ViewModel() {

    private var mutableAddress = MutableLiveData("Fetching location")
    private var mutableZone = MutableLiveData("Fetching zone")
    val address: LiveData<String> get() = mutableAddress
    val zone: LiveData<String> get() = mutableZone

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
        val streetName = address.split(",")[0].filter { it.isLetter() }

        mutableZone.value = when {
            firstZone.contains(streetName) -> "1"
            secondZone.contains(streetName) -> "2"
            thirdZone.contains(streetName) -> "3"
            else -> "none"
        }
    }
}