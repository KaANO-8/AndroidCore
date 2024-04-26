package com.kaano8.androidcore.com.kaano8.androidcore.service.model

sealed class TimerUiState {
    data object Start: TimerUiState()
    data object Pause: TimerUiState()
    data object Stop: TimerUiState()
}