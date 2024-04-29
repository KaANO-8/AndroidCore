package com.kaano8.androidcore.com.kaano8.androidcore.locationService.extensions

import android.location.Location
import android.os.Looper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

fun FusedLocationProviderClient.locationAsFlow() = callbackFlow<Location> {
    val callback = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult) {
            super.onLocationResult(result)
            for (location in result.locations) {
                trySend(location)
            }
        }
    }

    requestLocationUpdates(LocationRequest(), callback, Looper.getMainLooper()).addOnFailureListener {
        close(it)
    }

    awaitClose { removeLocationUpdates(callback)  } // clean up when Flow collection ends
}