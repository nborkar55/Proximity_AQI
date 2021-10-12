package com.nikhil.proximity_aqi

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.request.RequestOptions


fun ImageView.load(url: Int) {
    val options: RequestOptions = RequestOptions()
        .centerCrop()
        .transform(FitCenter())
        .placeholder(R.drawable.ic_launcher_foreground)
        .error(R.drawable.ic_launcher_foreground)

    Glide.with(this).load(url).apply(options).into(this)

}