package com.memes.memessharing

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApp:Application() {
    override fun onCreate() {
        super.onCreate()
    }
}