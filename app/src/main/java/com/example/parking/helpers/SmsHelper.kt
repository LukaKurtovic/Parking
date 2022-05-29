package com.example.parking.helpers

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.telephony.SmsManager
import com.example.parking.utils.ExtendTicketService
import com.example.parking.utils.SharedPrefs
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SmsHelper @Inject constructor(
    @ApplicationContext private val context: Context,
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
