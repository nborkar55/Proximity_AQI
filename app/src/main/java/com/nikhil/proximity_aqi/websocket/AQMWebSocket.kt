package com.nikhil.proximity_aqi.websocket

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.nikhil.proximity_aqi.constants.Constants.api_url
import com.nikhil.proximity_aqi.livedata.Util
import com.nikhil.proximity_aqi.model.AQIModelItem
import com.nikhil.proximity_aqi.moshi.MoshiHelper
import com.squareup.moshi.JsonEncodingException
import okhttp3.*
import okio.ByteString
import java.util.concurrent.TimeUnit


object AQMWebSocket : WebSocketListener() {
    private const val DEFAULT_TIMEOUT_IN_SECONDS = 20L
    private const val NORMAL_CLOSURE_STATUS = 1000
    private val data = ArrayList<AQIModelItem>()
    override fun onOpen(webSocket: WebSocket, response: Response) {
        webSocket.send("Hi Server")
        Log.d("TAG", "Sent Hi")

        //  webSocket.close(NORMAL_CLOSURE_STATUS, "Goodbye !")
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        try {
            Log.d("AQMWebSocket", "onMessage: Receiving : $text")
            data.clear()
            data.addAll(MoshiHelper.jsonAdapter.fromJson(text)!!)

            Util.showOutput(data)
            Log.d("AQMWebSocket", "onMessage: POJO created: $data")

        } catch (ex: JsonEncodingException) {
            Log.d("AQMWebSocket", "onMessage: Ignoring nonJson : $text")
        } catch (e: Exception) {
            Log.d("AQMWebSocket", "onMessage: Exception : $e")
        }
    }

    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
        Log.d("AQMWebSocket", "onMessage: Receiving bytes : " + bytes.hex())
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        webSocket.close(NORMAL_CLOSURE_STATUS, null)
        Log.d("AQMWebSocket", "onClosing: Receiving bytes : Closing : $code / $reason")
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        Log.d("AQMWebSocket", "onFailure: Error : " + t.message)
    }

    private val request = Request.Builder().url(api_url).build()

    private var okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .followRedirects(true)
        .followSslRedirects(false)
        .retryOnConnectionFailure(true)
        .connectTimeout(DEFAULT_TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
        .readTimeout(DEFAULT_TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
        .writeTimeout(DEFAULT_TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
        .build()

    fun getWebSocket() = okHttpClient.newWebSocket(request, AQMWebSocket)
}