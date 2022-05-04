package com.example.parking.ui.profile

import androidx.lifecycle.ViewModel
import com.example.parking.utils.SharedPrefs
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val prefs: SharedPrefs
) : ViewModel() {

    fun addLicence(licence: String) {
        prefs.addLicence(licence)
    }

    fun getLicence() = prefs.getLicence()
}