package com.kaano8.androidcore.com.kaano8.androidcore.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.kaano8.androidcore.R
import com.kaano8.androidcore.com.kaano8.androidcore.main.MainActivity

class NotificationHelper(private val context: Context) {
    private val notificationManager by lazy {
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    private val contentIntent: PendingIntent by lazy {
        PendingIntent.getActivity(
            context,
            0,
            Intent(context, MainActivity::class.java),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    private fun createChannel() =
        NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = CHANNEL_DESCRIPTION
            setSound(null, null)
        }

    private val notificationBuilder: NotificationCompat.Builder by lazy {
        NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle(context.getString(R.string.app_name))
            .setSound(null)
            .setContentIntent(contentIntent)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
    }

    fun getNotification(): Notification {
        notificationManager.createNotificationChannel(createChannel())

        return notificationBuilder.build()
    }

    fun updateNotification(notificationText: String? = null) {
        notificationText?.let { notificationBuilder.setContentText(it) }
        notificationManager.notify(TIMER_NOTIFICATION_ID, notificationBuilder.build())
    }


    companion object {
        const val TIMER_NOTIFICATION_ID = 99
        private const val CHANNEL_ID = "StarWarsChannel"
        private const val CHANNEL_NAME = "StarWarsChannelName"
        private const val CHANNEL_DESCRIPTION = "StarWarsChannelDescription"
    }
}