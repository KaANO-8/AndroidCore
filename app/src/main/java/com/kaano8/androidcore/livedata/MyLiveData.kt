package com.kaano8.androidcore.com.kaano8.androidcore.livedata

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner

class MyLiveData<T> {

    private var observers: HashMap<(T?) -> Unit, LifecycleOwner> = hashMapOf()

    var value: T? = null
        set(value) {
            field = value
            for ((observer, owner) in observers) {
                if (owner.lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED))
                    observer.invoke(value)
            }
        }

    fun observe(observer: (T?) -> Unit, owner: LifecycleOwner) {
        observers[observer] = owner
    }

    fun removeObserver(observer: (T?) -> Unit) {
        observers.remove(observer)
    }


}