package com.kaano8.androidcore.handler

import android.os.Handler
import android.os.HandlerThread

class Worker: HandlerThread(TAG) {

    private lateinit var handler: Handler

    init {
        start()
        handler = Handler(looper)
    }

    fun execute(task: Runnable): Worker {
        handler.post(task)
        return this
    }

    companion object {
        private const val TAG = "Worker"
    }
}