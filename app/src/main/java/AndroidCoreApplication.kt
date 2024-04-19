package com.kaano8.androidcore

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import javax.inject.Inject

@HiltAndroidApp
class AndroidCoreApplication : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.INFO)
            .setWorkerFactory(workerFactory)
            .build()

    companion object {
        private lateinit var executorService: ExecutorService

        fun getExecutor(): Executor {
            if (!::executorService.isInitialized)
                executorService = Executors.newFixedThreadPool(4)
            return executorService
        }
    }
}
