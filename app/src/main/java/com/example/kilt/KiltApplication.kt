package com.example.kilt

import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp
import ru.dgis.sdk.Context
import ru.dgis.sdk.DGis
import ru.dgis.sdk.platform.LogLevel
import ru.dgis.sdk.platform.LogOptions

@HiltAndroidApp
class KiltApplication : Application() {
    lateinit var sdkContext: Context
        private set

    override fun onCreate() {
        super.onCreate()
        try {
            sdkContext = DGis.initialize(
                applicationContext,
                logOptions = LogOptions(
                    customLevel = LogLevel.WARNING
                )
            )
        } catch (e: Exception) {
            Log.e("KiltApplication", "Failed to initialize DGis SDK", e)
        }
    }
}

val Application.sdkContext: Context
    get() = (this as KiltApplication).sdkContext
