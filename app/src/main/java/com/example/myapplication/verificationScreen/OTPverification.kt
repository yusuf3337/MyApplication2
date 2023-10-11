package com.example.myapplication.verificationScreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.myapplication.R
import com.example.myapplication.passwordScreen.ForgotPassword
import com.example.myapplication.passwordScreen.ResetPassword

class OTPverification : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otpverification)
    }
    fun verify(view : View){
        val intent = Intent(this, ResetPassword::class.java)
        startActivity(intent)
        finish()
    }

    fun backEmail (view: View){
        val intent = Intent(this, ForgotPassword::class.java)
        startActivity(intent)
        finish()
    }
}