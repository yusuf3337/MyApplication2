package com.example.myapplication.gif

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.myapplication.loginScreen.MainActivity
import com.example.myapplication.R
import com.example.myapplication.loginScreen.HomeScreen
import com.example.myapplication.loginScreen.LoginPage
import com.google.firebase.auth.FirebaseAuth

class splashgif : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashgif)
        auth = FirebaseAuth.getInstance()

        val delayInMillis = 4000L // 4 saniye gecikme süresi
Handler(Looper.getMainLooper()).postDelayed({


    checkUser()

 /*val intent = Intent(this@splashgif, MainActivity::class.java)
 startActivity(intent)
    finish()*/
}, delayInMillis)

    }




    private fun checkUser() {
        val currentUser = auth.currentUser
        println("merhaba kardes ")
        if (currentUser != null) {
            if (currentUser.isEmailVerified) {
                // E-posta adresi doğrulanmışsa
                val intent = Intent(this, HomeScreen::class.java)
                startActivity(intent)
                finish()
            } else {
                // E-posta adresi doğrulanmamışsa, doğrulama ekranına yönlendirin veya uygun bir mesaj gösterin.
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        } else {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}


