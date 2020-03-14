package com.maxciv.githubuserlist

import android.app.Application
import timber.log.Timber

/**
 * @author maxim.oleynik
 * @since 14.03.2020
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
