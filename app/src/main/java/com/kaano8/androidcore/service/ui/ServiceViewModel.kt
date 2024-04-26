package com.kaano8.androidcore.com.kaano8.androidcore.service.ui

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.IBinder
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kaano8.androidcore.com.kaano8.androidcore.service.progressservice.ProgressService

class ServiceViewModel : ViewModel() {
    var isReceiverRegistered: Boolean = false

    private val _elapsedTime = MutableLiveData<Int>()
    val elapsedTime: LiveData<Int>
        get() = _elapsedTime

    private val _isProgressBarUpdating = MutableLiveData<Boolean>()
    val isProgressBarUpdating: LiveData<Boolean>
        get() = _isProgressBarUpdating

    private val _binder = MutableLiveData<ProgressService.ProgressBinder?>()
    val binder: LiveData<ProgressService.ProgressBinder?>
        get() = _binder

    val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(p0: ComponentName?, iBinder: IBinder?) {
            _binder.value = iBinder as? ProgressService.ProgressBinder
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            _binder.value = null
        }
    }

    fun setIsProgressBarUpdating(value: Boolean) {
        _isProgressBarUpdating.value = value
    }

    fun updateElapsedTime(elapsedTime: Int) {
        _elapsedTime.value = elapsedTime
    }
}