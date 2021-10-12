package com.nikhil.proximity_aqi.model


import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AQIModelItem(
    @Json(name = "aqi")
    val aqi: Double?,
    @Json(name = "city")
    val city: String?
) : Parcelable
