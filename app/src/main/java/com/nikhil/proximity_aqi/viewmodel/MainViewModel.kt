package com.nikhil.proximity_aqi.viewmodel

import androidx.lifecycle.ViewModel
import com.nikhil.proximity_aqi.repository.Repository

class MainViewModel : ViewModel() {

    private val repository = Repository()

    fun sendSubscribe() = repository.sendSubscribe()

}