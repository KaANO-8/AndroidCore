package com.kaano8.androidcore.backgroundthread

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kaano8.androidcore.AndroidCoreApplication
import com.kaano8.androidcore.backgroundthread.repository.Repository

class BackgroundThreadViewModel : ViewModel() {

    private val _response: MutableLiveData<Repository.Result<String>> = MutableLiveData()
    val response: LiveData<Repository.Result<String>>
        get() = _response
    private val repository = Repository(AndroidCoreApplication.getExecutor())

    // Making request without coroutines
    fun makeRequest() {
        _response.value = Repository.Result.Loading
        repository.makeRequest {
            _response.postValue(it)
        }
    }
}
