package com.nikhil.proximity_aqi.livedata

import android.content.Intent
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.nikhil.proximity_aqi.constants.Constants.LIVEDATA_KEY
import java.util.*

class CommonEventBus {
    // Publish an object to the specified subject for all subscribers of that subject.
    /**
     *
     * */
    private fun publish(subject: String, intent: Intent) {
        getLiveData(subject).update(intent)
    }

    fun pushData(intent: Intent) {
        // publisher.onNext(intent)
        publish(LIVEDATA_KEY, intent)
    }

    companion object {

        private val sSubjectMap = HashMap<String, LiveDataEvents>()

        private var instance: CommonEventBus? = null

        @JvmStatic
        fun getInstance(): CommonEventBus {
            return instance ?: synchronized(this) {
                instance ?: CommonEventBus().also { instance = it }
            }
        }

        @JvmStatic
        // Get the live data or create it if it's not already in memory.
        private fun getLiveData(subjectCode: String): LiveDataEvents {
            var liveData: LiveDataEvents? = sSubjectMap[LIVEDATA_KEY]
            if (liveData == null) {
                liveData = LiveDataEvents(subjectCode)
                sSubjectMap[subjectCode] = liveData
            }
            return liveData
        }

        @JvmStatic
        // Subscribe to the specified subject and listen for updates on that subject.
        fun subscribe(subject: String, lifecycle: LifecycleOwner, action: Observer<Intent>) {
            getLiveData(subject).observe(lifecycle, action)
        }

        @JvmStatic
        // Removes this subject when it has no observers.
        fun unregister(subject: String) {
            sSubjectMap.remove(subject)
        }
    }
}
