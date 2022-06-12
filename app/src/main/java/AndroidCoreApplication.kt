package com.kaano8.androidcore

import android.app.Application
import com.kaano8.androidcore.com.kaano8.androidcore.room.database.WordRoomDatabase
import com.kaano8.androidcore.com.kaano8.androidcore.room.repository.WordRepository
import dagger.hilt.android.HiltAndroidApp
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@HiltAndroidApp
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
