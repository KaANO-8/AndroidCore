package com.kaano8.androidcore.com.kaano8.androidcore.service.timerservice

import androidx.lifecycle.ViewModel
import com.kaano8.androidcore.com.kaano8.androidcore.service.model.TimerState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class TimerServiceViewModel @Inject constructor() : ViewModel() {
    private val _timerState: MutableStateFlow<TimerState> = MutableStateFlow(TimerState.INITIALIZED)
    val timerState: StateFlow<TimerState>
        get() = _timerState



}