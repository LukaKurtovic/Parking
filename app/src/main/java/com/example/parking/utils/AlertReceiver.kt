package com.example.parking.utils

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.parking.R
import com.example.parking.helpers.NotificationHelper

class AlertReceiver : BroadcastReceiver() {
    @SuppressLint("UnspecifiedImmutableFlag")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context?, intent: Intent?) {
        val notificationHelper = NotificationHelper(context)
        val nb = notificationHelper.getChannelNotification(
            context!!.getString(R.string.notification_title),
            context.getString(R.string.notification_message)
        )
        notificationHelper.getManager().notify(1, nb.build())
    }
}