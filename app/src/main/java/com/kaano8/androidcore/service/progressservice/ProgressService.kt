package com.kaano8.androidcore.com.kaano8.androidcore.service.progressservice

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log

private const val TAG = "ProgressService"

class ProgressService : Service() {

    private val binder: ProgressBinder by lazy { ProgressBinder() }
    private val handler: Handler by lazy { Handler(Looper.getMainLooper()) }
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

    fun resumePretendLongRunningTask() {
        isPaused = false
        startPretendLongRunningTask()
    }

    fun startPretendLongRunningTask() {
        val runnable = object : Runnable {
            override fun run() {
                if (progress >= maxProgress || isPaused) {
                    Log.d(TAG, "run: removing callbacks")
                    handler.removeCallbacks(this)
                    pausePretendLongRunningTask()
                } else {
                    Log.d(TAG, "run: progress: $progress")
                    progress += 100
                    handler.postDelayed(this, 100)
                }
            }
        }
        // start progressing
        handler.postDelayed(runnable, 100)
    }

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
}