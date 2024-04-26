package com.kaano8.androidcore.com.kaano8.androidcore.service.timerservice

import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.kaano8.androidcore.R
import com.kaano8.androidcore.com.kaano8.androidcore.extensions.secondsToTime
import com.kaano8.androidcore.com.kaano8.androidcore.notification.NotificationHelper
import com.kaano8.androidcore.com.kaano8.androidcore.service.model.TimerState
import com.kaano8.androidcore.com.kaano8.androidcore.service.ui.ServiceFragment.Companion.TIMER_ACTION
import com.kaano8.androidcore.com.kaano8.androidcore.service.ui.ServiceFragment.Companion.UPDATED_TIMER_VALUE
import com.kaano8.androidcore.service.timerservice.TimerService
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.CancellationException

private const val TAG = "TimerServiceWithCoroutine"

class TimerServiceWithCoroutine: Service() {

    private val state = MutableStateFlow(TimerState.INITIALIZED)
    private var currentTime = 0

    private val notificationHelper: NotificationHelper by lazy { NotificationHelper(this) }

    private val supervisorJob = SupervisorJob()
    private val serviceCoroutineScope =
        CoroutineScope(CoroutineName("Service coroutine") + supervisorJob)

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate() called")
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        Log.d(TAG, "onStartCommand() called")

        intent?.extras?.run {
            when (getSerializable(TimerService.SERVICE_COMMAND, TimerState::class.java)) {
                TimerState.START -> startTimer()
                TimerState.STOP -> stopTimer()
                TimerState.PAUSE -> pauseTimer()
                else -> return START_NOT_STICKY
            }
        }
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
        supervisorJob.children.forEach { it.cancel(CancellationException("Cancelled as user pressed stop")) }
    }

    private fun startTimer() {
        // avoid duplicate calling
        if (state.value == TimerState.START)
            return

        // update the state
        state.value = TimerState.START

        // start foreground service
        startForeground(NotificationHelper.TIMER_NOTIFICATION_ID, notificationHelper.getNotification())

        // start the real work
        serviceCoroutineScope.launch {
            while (true) {
                broadcastTime(currentTime, state)
                delay(1_000)
                currentTime++
            }
        }
    }

    private fun pauseTimer() {
        state.value = TimerState.PAUSE
        broadcastTime(currentTime, state)
        supervisorJob.children.forEach { it.cancel(CancellationException("Cancelled as user pressed stop")) }
    }

    private fun stopTimer() {
        state.value = TimerState.STOP
        currentTime = 0
        broadcastTime(currentTime, state)
        stopForeground(true)
        supervisorJob.children.forEach { it.cancel(CancellationException("Cancelled as user pressed stop")) }
    }


    // Since this is just a foregroundService, and not bound
    // we could return null as binder
    override fun onBind(p0: Intent?): IBinder? = null

    private fun broadcastTime(currentTime: Int, state: StateFlow<TimerState>) {
        when (state.value) {
            TimerState.START -> {
                sendBroadcastUsingLocalBroadcastManager(currentTime)

                // Update notification
                notificationHelper.updateNotification(
                    getString(
                        R.string.time_is_running,
                        currentTime.secondsToTime()
                    )
                )
            }

            TimerState.PAUSE -> {
                notificationHelper.updateNotification(getString(R.string.get_back))
            }

            TimerState.STOP -> {
                sendBroadcastUsingLocalBroadcastManager(0)
            }

            else -> {
                // No action needed
            }
        }
    }

    private fun sendBroadcastUsingLocalBroadcastManager(timerValue: Int) {
        // build the intent
        val intent = Intent(TIMER_ACTION).also { it.putExtra(UPDATED_TIMER_VALUE, timerValue) }
        // broadcast the intent. Not the correct way to communicate back to activity,
        // prefer using bound services
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }
}