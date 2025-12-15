package com.example.bjsdk

import android.content.Context
import android.content.Intent
import com.example.bjsdk.presentation.activities.MainActivity

object MySdk {

    fun init(context: Context) {
        SdkProvider.init(context)
    }

    fun openMainScreen(context: Context) {
        val i = Intent(context, MainActivity::class.java)
        context.startActivity(i)
    }
}