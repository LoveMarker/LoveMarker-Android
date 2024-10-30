package com.capstone.lovemarker

import android.app.Application
import timber.log.Timber

class LoveMarkerApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        initTimber()
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}