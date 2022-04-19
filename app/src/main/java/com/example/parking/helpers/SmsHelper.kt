package com.example.parking.helpers

import android.telephony.SmsManager
import javax.inject.Inject

class SmsHelper @Inject constructor(
    private val smsManager: SmsManager
) {
    fun sendSMS(zone: String) {
        val licenceNumber = "OS-123-AA"
        val destinationNumber = when (zone) {
            "1" -> "1"
            "2" -> "2"
            "3" -> "3"
            else -> "0"
        }
        smsManager.sendTextMessage(destinationNumber, null, licenceNumber, null, null)
    }
}