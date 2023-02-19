package com.semba.dkbimages

import android.app.Application
import timber.log.Timber

class DkbImagesApp: Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}