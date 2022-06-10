package com.kaano8.androidcore

import android.app.Application
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class AndroidCoreApplication : Application() {


    companion object {
        private lateinit var executorService: ExecutorService

        fun getExecutor(): Executor {
            if (!::executorService.isInitialized)
                executorService = Executors.newFixedThreadPool(4)
            return executorService
        }
    }
}
