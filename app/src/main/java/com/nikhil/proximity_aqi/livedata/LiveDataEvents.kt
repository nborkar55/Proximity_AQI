package com.nikhil.proximity_aqi.livedata

import android.content.Intent
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.nikhil.proximity_aqi.livedata.CommonEventBus

class LiveDataEvents(val subject: String) : MutableLiveData<Intent>() {
    private var mSubject: String = ""

    fun liveDataEvents(subject: String) {
        mSubject = subject
    }

    fun update(intent: Intent?) {
        postValue(intent)
    }

    override fun removeObservers(owner: LifecycleOwner) {
        super.removeObservers(owner)
        if (hasObservers()) {
            CommonEventBus.unregister(subject)
        }
    }
}
