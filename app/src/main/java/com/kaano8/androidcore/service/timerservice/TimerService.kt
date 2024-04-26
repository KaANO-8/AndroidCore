package com.kaano8.androidcore.service.timerservice

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.kaano8.androidcore.R
import com.kaano8.androidcore.com.kaano8.androidcore.extensions.secondsToTime
import com.kaano8.androidcore.com.kaano8.androidcore.notification.NotificationHelper
import com.kaano8.androidcore.com.kaano8.androidcore.notification.NotificationHelper.Companion.TIMER_NOTIFICATION_ID
import com.kaano8.androidcore.com.kaano8.androidcore.service.model.TimerState
import com.kaano8.androidcore.com.kaano8.androidcore.service.ui.ServiceFragment.Companion.NOTIFICATION_TEXT
import com.kaano8.androidcore.com.kaano8.androidcore.service.ui.ServiceFragment.Companion.TIMER_ACTION
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TimerService : Service() {

    private var timerState = TimerState.INITIALIZED
    private var currentTime: Int = 0
    private var startedAtTimestamp: Int = 0
        set(value) {
            currentTime = value
            field = value
        }
    private val handler: Handler by lazy { Handler(Looper.getMainLooper()) }
    private val notificationHelper: NotificationHelper by lazy { NotificationHelper(this) }
    private val runnable: Runnable = object : Runnable {
        override fun run() {
            broadcastUpdate()
            currentTime++
            // Repeat every 1 second
            handler.postDelayed(this, 1000)
        }
    }

    // It's a foreground service that's why no need to impl onBind()
    override fun onBind(intent: Intent): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)

        intent?.extras?.run {
            when (getSerializable(SERVICE_COMMAND) as TimerState) {
                TimerState.START -> startTimer()
                TimerState.STOP -> stopTimer()
                TimerState.PAUSE -> pauseTimer()
                else -> return START_NOT_STICKY
            }
        }
        return START_NOT_STICKY
    }

    private fun startTimer(elapsedTime: Int? = null) {
        timerState = TimerState.START

        startedAtTimestamp = elapsedTime ?: 0

        startForeground(TIMER_NOTIFICATION_ID, notificationHelper.getNotification())

        startCoroutineTimer()
    }

    private fun stopTimer() {
        timerState = TimerState.STOP
        handler.removeCallbacks(runnable)
        broadcastUpdate()
        stopService()
    }

    private fun stopService() {
        stopForeground(true)
    }

    private fun pauseTimer() {
        timerState = TimerState.PAUSE
        handler.removeCallbacks(runnable)
        broadcastUpdate()
    }

    private fun broadcastUpdate() {
        if (timerState == TimerState.START) {
            // count elapsed time
            val elapsedTime = (currentTime - startedAtTimestamp)

            LocalBroadcastManager.getInstance(this).sendBroadcast(Intent(TIMER_ACTION).also {
                it.putExtra(
                    NOTIFICATION_TEXT,
                    elapsedTime
                )
            })

            notificationHelper.updateNotification(
                getString(
                    R.string.time_is_running,
                    elapsedTime.secondsToTime()
                )
            )
        } else if (timerState == TimerState.PAUSE) {
            notificationHelper.updateNotification(getString(R.string.get_back))
        }
    }

    private fun startCoroutineTimer() {
        handler.post(runnable)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(runnable)
    }

    companion object {
        const val SERVICE_COMMAND = "serviceCommand"
    }
}