package com.capstone.lovemarker

import android.app.Application
import com.google.android.libraries.places.api.Places
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class LoveMarkerApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        initTimber()
        initPlacesSdk()
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun initPlacesSdk() {
        val mapsApiKey = BuildConfig.MAPS_API_KEY
        if (mapsApiKey.isEmpty()) {
            Timber.e("maps api key is empty.")
            return
        }
        Places.initializeWithNewPlacesApiEnabled(applicationContext, mapsApiKey)
    }
}
