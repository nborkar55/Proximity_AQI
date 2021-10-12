package com.nikhil.proximity_aqi.livedata

import android.content.Intent
import android.util.Log
import com.nikhil.proximity_aqi.constants.Constants.RESPONSE
import com.nikhil.proximity_aqi.model.AQIModelItem


object Util {
    fun showOutput(model: ArrayList<AQIModelItem>) {
        CommonEventBus.getInstance().pushData(
            Intent().apply {
                putExtra("action", RESPONSE)
                putParcelableArrayListExtra("data", model)
                Log.d(
                    "Util:showOutput",
                    "Utility: action: $RESPONSE data: $model"
                )
            }
        )
    }
}