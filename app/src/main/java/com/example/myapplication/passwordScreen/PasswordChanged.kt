package com.example.myapplication.passwordScreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.myapplication.loginScreen.LoginPage
import com.example.myapplication.R

class PasswordChanged : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password_changed)
    }
    fun backLogin(view : View){
        val intent = Intent(this, LoginPage::class.java)
        startActivity(intent)
        finish()
    }
}