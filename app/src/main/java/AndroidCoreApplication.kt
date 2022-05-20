package com.kaano8.androidcore

import android.app.Application
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class AndroidCoreApplication: Application() {
    val executorService: ExecutorService = Executors.newFixedThreadPool(4)
}