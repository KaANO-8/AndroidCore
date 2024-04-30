package com.kaano8.androidcore.com.kaano8.androidcore.livedata

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import java.util.concurrent.ConcurrentHashMap

class MyLiveData<T> {
    private val observers = ConcurrentHashMap<(T?) -> Unit, LifecycleObserver>()

    private var value: T? = null
        set(value) {
            field = value

            for ((observer, lifecycleObserver) in observers) {
                if (lifecycleObserver.owner.lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED))
                    observer.invoke(value)
            }
        }

    fun getValue(): T? = value

    fun observe(lifecycleOwner: LifecycleOwner, observer: (T?) -> Unit) {
        val lifecycleObserver = LifecycleObserver(lifecycleOwner, observer)
        lifecycleOwner.lifecycle.addObserver(lifecycleObserver)
        observers[observer] = lifecycleObserver
    }

    fun removeObserver(observer: (T?) -> Unit) {
        val lifecycleObserver = observers.remove(observer)
        lifecycleObserver?.owner?.lifecycle?.removeObserver(lifecycleObserver)
    }

    private fun notifyChange(lifecycleObserver: LifecycleObserver) {
        lifecycleObserver.observer.invoke(value)
    }

    private inner class LifecycleObserver(val owner: LifecycleOwner, val observer: (T?) -> Unit): DefaultLifecycleObserver {
        override fun onStart(owner: LifecycleOwner) {
            super.onStart(owner)
            notifyChange(this)
        }

        override fun onResume(owner: LifecycleOwner) {
            super.onResume(owner)
            notifyChange(this)
        }

        override fun onDestroy(owner: LifecycleOwner) {
            super.onDestroy(owner)
            removeObserver(observer)
        }
    }
}