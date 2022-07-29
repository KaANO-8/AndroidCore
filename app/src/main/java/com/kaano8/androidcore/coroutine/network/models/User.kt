package com.kaano8.androidcore.com.kaano8.androidcore.coroutine.network.models

import androidx.annotation.Keep

@Keep
data class User(
    val login: String,
    val contributions: Int
)