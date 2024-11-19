package com.example.kilt

import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp
import ru.dgis.sdk.Context
import ru.dgis.sdk.DGis
import ru.dgis.sdk.platform.LogLevel
import ru.dgis.sdk.platform.LogOptions
import ru.dgis.sdk.platform.LogSink

@HiltAndroidApp
class KiltApplication:Application() {
    lateinit var sdkContext: Context


    override fun onCreate() {
        super.onCreate()
        sdkContext = initializeDGis(this)
        try {
            // Инициализация 2ГИС SDK
            DGis.initialize(applicationContext)
        } catch (e: Exception) {
            Log.e("KiltApplication", "Failed to initialize DGis SDK", e)
        }

    }
    fun initializeDGis(appContext: android.content.Context): Context {
        return DGis.initialize(
            appContext,
            logOptions = LogOptions(
                customLevel = LogLevel.WARNING,
                customSink = createLogSink(),


            )
        )
    }
    fun createLogSink(): LogSink? {
        return null
    }


}
val Application.sdkContext: Context
    get() = (this as com.example.kilt.KiltApplication).sdkContext