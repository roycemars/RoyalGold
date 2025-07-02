package com.roycemars.royalgold

import android.app.Application
import android.util.Log
import com.roycemars.royalgold.data.AppContainer
import com.roycemars.royalgold.data.AppContainerImpl

class RoyalGoldApp: Application() {
    // AppContainer instance used by the rest of classes to obtain dependencies
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppContainerImpl(this)
        Log.d("RoyalGoldApp", "AppContainer initialized")
    }
}