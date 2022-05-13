package com.example.parking.helpers

import android.telephony.SmsManager
import com.example.parking.utils.SharedPrefs
import javax.inject.Inject

class SmsHelper @Inject constructor(
    private val smsManager: SmsManager,
    private val prefs: SharedPrefs
) {
    fun sendSMS(zone: String) {
        val licenceNumber = prefs.getLicence()
        val destinationNumber = when (zone) {
            // TODO: Move to constants
            "1" -> "1"
            "2" -> "2"
            "3" -> "3"
            else -> "0"
        }
        smsManager.sendTextMessage(destinationNumber, null, licenceNumber, null, null)
    }
}