package com.kaano8.androidcore.handler

import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log

class SimpleLooper: Thread(TAG) {

    private lateinit var handler: Handler

    init {
        start()
    }

    override fun run() {
        Looper.prepare()
        handler = Handler(Looper.myLooper()!!) {
            Log.d(TAG, "run: ${it.obj}")
            true
        }
        Looper.loop()
    }

    fun execute(message: String): SimpleLooper {
        // adding manual delay to let looper setup
        sleep(1_000)

        val msg = Message.obtain()
        msg.obj = message
        if (::handler.isInitialized)
            handler.sendMessage(msg)
        return this
    }

    companion object {
        private const val TAG = "SimpleLooper"
    }
}