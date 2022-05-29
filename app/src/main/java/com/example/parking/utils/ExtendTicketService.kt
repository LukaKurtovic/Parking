package com.example.parking.utils

import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.widget.Toast
import com.example.parking.helpers.*
import javax.inject.Inject

class ExtendTicketService : Service() {
    private lateinit var notificationManager: NotificationManager

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent?.getStringExtra(EXTEND_KEY) != null) {
            notificationManager.cancelAll()
            Toast.makeText(applicationContext, "Extended for 1 hour", Toast.LENGTH_SHORT).show()
        }
        return START_NOT_STICKY
    }
}