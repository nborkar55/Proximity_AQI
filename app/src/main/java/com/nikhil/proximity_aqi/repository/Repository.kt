package com.nikhil.proximity_aqi.repository

import androidx.lifecycle.LiveData
import com.nikhil.proximity_aqi.model.AQIModelItem
import com.nikhil.proximity_aqi.websocket.AQMWebSocket
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

class Repository : CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    fun sendSubscribe()= AQMWebSocket.getWebSocket()

}