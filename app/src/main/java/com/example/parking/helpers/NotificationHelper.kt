package com.example.parking.helpers

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.parking.R
import com.example.parking.ui.MainActivity

const val EXTEND_KEY = "extend"
const val EXTEND_VALUE = "extend"

@RequiresApi(Build.VERSION_CODES.O)
class NotificationHelper(base: Context?) : ContextWrapper(base) {
    private val channel =
        NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
    private var notificationManager: NotificationManager? = null

    init {
        channel.apply {
            enableLights(true)
            enableVibration(true)
            lightColor = R.color.purple_200
            lockscreenVisibility = Notification.VISIBILITY_PUBLIC
        }
        getManager().createNotificationChannel(channel)
    }

    fun getManager(): NotificationManager {
        if (notificationManager == null) {
            notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        }
        return notificationManager!!
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    fun getChannelNotification(title: String, message: String): NotificationCompat.Builder {
        val intent = Intent(applicationContext, MainActivity::class.java)
        intent.putExtra(EXTEND_KEY, EXTEND_VALUE)
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        val pendingIntent =
            PendingIntent.getActivity(applicationContext, 1, intent, PendingIntent.FLAG_ONE_SHOT)

        return NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(R.drawable.ic_car)
            .addAction(0, "Extend for 1 hour", pendingIntent)
    }
}

private const val CHANNEL_ID = "ChannelID"
private const val CHANNEL_NAME = "ChannelName"