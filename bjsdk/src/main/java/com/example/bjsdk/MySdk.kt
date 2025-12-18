package com.example.bjsdk

import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.bjsdk.api.di.sdkModule
import com.example.bjsdk.presentation.activities.MainActivity
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

object MySdk {

    private var initialized = false

    fun init(context: Context) {
        if (initialized) return

        startKoin {
            androidContext(context.applicationContext)
            modules(sdkModule)
        }

        initialized = true
        Log.d("BJSDKLOG", "SDK initialized with Koin")
    }

    fun openMainScreen(context: Context) {
        val intent = Intent(context, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }
}