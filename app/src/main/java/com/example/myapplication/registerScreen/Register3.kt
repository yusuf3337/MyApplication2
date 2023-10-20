package com.example.myapplication.registerScreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.myapplication.R
import com.example.myapplication.loginScreen.LoginPage

class Register3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register3)
    }

    fun goSuccessPage(view: View) {
        val intent = Intent(this,LoginPage::class.java)
        startActivity(intent)
    }
}