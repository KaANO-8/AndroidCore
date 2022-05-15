package com.kaano8.androidcore.handler

import android.util.Log
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.atomic.AtomicBoolean

class SimpleWorker: Thread(TAG) {
    private val aLive: AtomicBoolean = AtomicBoolean(true)
    private val taskQueue: ConcurrentLinkedQueue<Runnable> = ConcurrentLinkedQueue()

    init {
        start()
    }

    override fun run() {
        while (aLive.get()) {
            taskQueue.poll()?.run()
        }
        Log.d(TAG, "run: SimpleWorkerTerminated")
    }

    fun execute(runnable: Runnable): SimpleWorker {
        taskQueue.offer(runnable)
        return this
    }

    fun quit() {
        aLive.set(false)
    }

    companion object {
        private const val TAG = "SimpleWorker"
    }
}
