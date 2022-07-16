package com.kaano8.androidcore.service

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MemoViewModel : ViewModel() {
    var isReceiverRegistered: Boolean = false

    private val _elapsedTime = MutableLiveData<Int>()
    val elapsedTime: LiveData<Int>
        get() = _elapsedTime

    fun updateElapsedTime(elapsedTime: Int) {
        _elapsedTime.value = elapsedTime
    }
}