package com.example.myapplication.gif

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.myapplication.loginScreen.MainActivity
import com.example.myapplication.R

class splashgif : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashgif)

        val delayInMillis = 4000L // 4 saniye gecikme süresi
Handler(Looper.getMainLooper()).postDelayed({
 val intent = Intent(this@splashgif, MainActivity::class.java)
 startActivity(intent)
    finish()
}, delayInMillis)

    }
}