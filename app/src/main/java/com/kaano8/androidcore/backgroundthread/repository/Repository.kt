package com.kaano8.androidcore.backgroundthread.repository

import android.util.Log
import java.util.concurrent.Executor

class Repository(private val executor: Executor) {

    sealed class Result<out R> {
        data class Success<out T>(val data: T) : Result<T>()
        data class Error(val exception: Exception) : Result<Nothing>()
        object Loading : Result<Nothing>()
    }

    fun makeRequest(callback: (Result<String>) -> Unit) {
        Log.d("Repository", "makeRequest: ${Thread.currentThread().name }")
        executor.execute {
            Log.d("Repository", "makeRequest: ${Thread.currentThread().id}")
            callService()
            // We can use handler of main thread to post results also
            callback(Result.Success("Success"))
        }
    }

    // Fake API service
    private fun callService() {
        Thread.sleep(3_000)
    }
}