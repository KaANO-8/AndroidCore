package com.kaano8.androidcore.com.kaano8.androidcore.coroutine.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaano8.androidcore.com.kaano8.androidcore.coroutine.network.models.RequestData
import com.kaano8.androidcore.com.kaano8.androidcore.coroutine.network.models.User
import com.kaano8.androidcore.com.kaano8.androidcore.coroutine.repository.CoroutineDemoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.concurrent.thread

@HiltViewModel
class CoroutineDemoViewModel @Inject constructor(private val repository: CoroutineDemoRepository) :
    ViewModel() {

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>>
        get() = _users

    fun loadContributors() {
        viewModelScope.launch {
            val response = repository.loadContributorBlocking(RequestData("KaANO-8"))
            _users.value = response
        }
    }
}