package com.example.myapplication.presentation.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash_screen)

        val sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE)
        val isLogin = sharedPreferences.getBoolean("isLogin", false)
        if(isLogin){
            delaySplashScreen(
                Intent(this, MainActivity::class.java)
            )
        } else {
            delaySplashScreen(
                Intent(this, LoginActivity::class.java)
            )
        }
    }

    private fun delaySplashScreen(intent: Intent) {
        Handler(Looper.getMainLooper()).postDelayed(
            {
                startActivity(intent)
                finish()
            }, 3000)
    }
}