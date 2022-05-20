package com.kaano8.androidcore.backgroundthread.repository

import java.util.concurrent.Executor

class Repository {

    sealed class Result<out R> {
        data class Success<out T>(val data: T) : Result<T>()
        data class Error(val exception: Exception) : Result<Nothing>()
        object Loading : Result<Nothing>()
    }

    fun makeRequest(callback: (Result<String>) -> Unit) {
        Executor {
            callService()
            callback(Result.Success("Success"))
        }
    }

    // Fake API service
    private fun callService() {
        Thread.sleep(3_000)
    }
}