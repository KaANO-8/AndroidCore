package com.kaano8.androidcore.com.kaano8.androidcore.service.progressservice

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*

private const val TAG = "ProgressService"

class ProgressService : Service() {

    private val binder: ProgressBinder by lazy { ProgressBinder() }
    private val _progressServiceState = MutableStateFlow(ProgressServiceState.START)
    val progressServiceState: StateFlow<ProgressServiceState>
        get() = _progressServiceState
    var progress = 0
        private set
    val maxProgress = 5000
    var isPaused: Boolean = true
        private set

    override fun onBind(intent: Intent?): IBinder {
        Log.d(TAG, "onBind: called")
        return binder
    }

    inner class ProgressBinder : Binder() {
        fun getService(): ProgressService = this@ProgressService
    }

    fun pausePretendLongRunningTask() {
        isPaused = true
    }

    suspend fun resumePretendLongRunningTask(): Flow<Int> {
        isPaused = false
        return startPretendLongRunningTask()
    }

    private suspend fun startPretendLongRunningTask() = flow {
        while (true) {
            if (progress >= maxProgress || isPaused) {
                Log.d(TAG, "Cancelling flow")
                pausePretendLongRunningTask()
                break
            } else {
                progress += 100
                Log.d(TAG, "Emitting $progress")
                emit(progress)
                delay(100)
            }
        }
    }.flowOn(Dispatchers.Default)

    fun resetTask() {
        progress = 0
    }

    /**
     * This will be called if app is killed
     */
    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        Log.d(TAG, "onTaskRemoved: called")
        stopSelf()
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.d(TAG, "onUnbind: called")
        return super.onUnbind(intent)
    }

    enum class ProgressServiceState {
        START,
        PAUSE,
        RESUME,
        RESTART
    }
}