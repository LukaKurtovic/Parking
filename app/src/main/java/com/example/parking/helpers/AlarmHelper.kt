package com.example.parking.helpers

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.parking.utils.AlertReceiver
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AlarmHelper @Inject constructor(
    @ApplicationContext private val context: Context,
    private val alarmManager: AlarmManager
) {
    @SuppressLint("UnspecifiedImmutableFlag")
    fun setAlarm(fiveMinutesBefore: Long) {
        val intent = Intent(context, AlertReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, 1, intent, 0)

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, fiveMinutesBefore, pendingIntent)
    }
}