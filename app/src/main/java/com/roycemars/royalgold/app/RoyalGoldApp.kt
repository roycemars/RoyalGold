package com.roycemars.royalgold.app

import android.app.Application
import android.util.Log
import com.roycemars.royalgold.app.di.AppContainer
import com.roycemars.royalgold.app.di.AppContainerImpl
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class RoyalGoldApp: Application() {
    // AppContainer instance used by the rest of classes to obtain dependencies
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppContainerImpl(this)
        Log.d("RoyalGoldApp", "AppContainer initialized")
    }
}