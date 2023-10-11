package com.example.myapplication.passwordScreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.myapplication.R

class ResetPassword : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)
    }

    fun resetButton(view : View){
        val intent = Intent(this, PasswordChanged::class.java)
        startActivity(intent)
        finish()
    }
}