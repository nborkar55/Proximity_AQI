package com.nikhil.proximity_aqi

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.nikhil.proximity_aqi.adapters.RowAdapter
import com.nikhil.proximity_aqi.constants.Constants.LIVEDATA_KEY
import com.nikhil.proximity_aqi.livedata.CommonEventBus
import com.nikhil.proximity_aqi.model.AQIModelItem
import com.nikhil.proximity_aqi.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.WebSocket

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private lateinit var ws: WebSocket
    private lateinit var mainViewModel: MainViewModel
    private var action: Boolean = true
    private val rowList = ArrayList<AQIModelItem>()
    private lateinit var rowAdapter: RowAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        myrecyclerview.layoutManager = LinearLayoutManager(
            this@MainActivity,
            LinearLayoutManager.VERTICAL,
            false
        )
        rowAdapter = RowAdapter(this, rowList)
        myrecyclerview.adapter = rowAdapter

        CommonEventBus.subscribe(LIVEDATA_KEY, this@MainActivity, {
            processCommonReceiver(it)
        })

        close.setOnClickListener {
            finish()
        }
        mainViewModel = ViewModelProvider(this@MainActivity).get(MainViewModel::class.java)
        if (isNetworkAvailable()) {
            tvInternet.visibility = View.GONE
            myrecyclerview.visibility = View.VISIBLE
            mainViewModel.sendSubscribe().also { ws = it }

            action = true
            actions.setImageResource(R.drawable.ic_pause)
        } else {
            action = false
            actions.setImageResource(R.drawable.ic_play_icon)
        }



        actions.setOnClickListener{
            action = if (action) {
                ws.cancel()
                actions.setImageResource(R.drawable.ic_play_icon)
                false
            } else {
                mainViewModel.sendSubscribe().also { ws = it }

                actions.setImageResource(R.drawable.ic_pause)
                true
            }
        }
    }

    private fun processCommonReceiver(intent: Intent) {
        if (intent.hasExtra("data")) {
            val model = intent.extras!!["data"] as ArrayList<AQIModelItem>
            rowList.clear()
            rowList.addAll(model)
            rowAdapter.notifyDataSetChanged()
        }

    }

    private fun isNetworkAvailable(): Boolean {
        val cm =
            applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val nInfo = cm.activeNetworkInfo
        return nInfo != null && nInfo.isAvailable && nInfo.isConnected
    }
}