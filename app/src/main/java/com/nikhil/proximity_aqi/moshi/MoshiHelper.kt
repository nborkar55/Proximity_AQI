package com.nikhil.proximity_aqi.moshi

import com.nikhil.proximity_aqi.model.AQIModelItem
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

object MoshiHelper {
    private var moshi: Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
    private val listType = Types.newParameterizedType(List::class.java, AQIModelItem::class.java)
    val jsonAdapter: JsonAdapter<List<AQIModelItem>> = moshi.adapter(listType)
}