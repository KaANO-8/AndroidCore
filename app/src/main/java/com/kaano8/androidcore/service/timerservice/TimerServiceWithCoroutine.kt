package com.kaano8.androidcore.com.kaano8.androidcore.service.timerservice

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.kaano8.androidcore.com.kaano8.androidcore.notification.NotificationHelper
import com.kaano8.androidcore.com.kaano8.androidcore.service.model.TimerState
import com.kaano8.androidcore.service.timerservice.TimerService
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.CancellationException

@AndroidEntryPoint
class TimerServiceWithCoroutine : Service() {

    val state = MutableStateFlow(TimerState.INITIALIZED)
    private var currentTime = 0

    private val notificationHelper: NotificationHelper by lazy { NotificationHelper(this) }

    private val serviceCoroutineScope = CoroutineScope(CoroutineName("Service coroutine"))
    private lateinit var timerJob: Job

    private fun startTimer() {
        state.value = TimerState.START
        timerJob = serviceCoroutineScope.launch {
            while (true) {
                delay(1_000)
                broadcastTime(currentTime, state.value)
                currentTime++
            }
        }
    }

    private fun pauseTimer() {
        state.value = TimerState.PAUSE
        timerJob.cancel(CancellationException("Cancelled as user pressed stop"))
    }

    private fun stopTimer() {
        state.value = TimerState.STOP
        currentTime = 0
        timerJob.cancel(CancellationException("Cancelled as user pressed stop"))
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)

        intent?.extras?.run {
            when (getSerializable(TimerService.SERVICE_COMMAND) as TimerState) {
                TimerState.START -> startTimer()
                TimerState.STOP -> stopTimer()
                TimerState.PAUSE -> pauseTimer()
                else -> return START_NOT_STICKY
            }
        }
        return START_NOT_STICKY
    }

    // Since this is just a foregroundService, and not bound
    // we could return null as binder
    override fun onBind(p0: Intent?): IBinder? = null

    private fun broadcastTime(currentTime: Int, state: TimerState) {
        when (state) {
            TimerState.START -> {

            }

            TimerState.PAUSE -> {

            }

            else -> {
                // No action needed
            }
        }
    }
}