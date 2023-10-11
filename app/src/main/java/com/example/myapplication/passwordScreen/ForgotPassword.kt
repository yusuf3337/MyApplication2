package com.example.myapplication.passwordScreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.myapplication.verificationScreen.OTPverification
import com.example.myapplication.R

class ForgotPassword : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
    }

    fun sendCode(view : View){
        val intent = Intent(this, OTPverification::class.java)
        startActivity(intent)
        finish()
    }
}
