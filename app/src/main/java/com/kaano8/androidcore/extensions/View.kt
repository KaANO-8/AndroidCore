package com.kaano8.androidcore.extensions

import android.view.View

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.disabled() {
    isEnabled = false
}

fun View.enabled() {
    isEnabled = true
}