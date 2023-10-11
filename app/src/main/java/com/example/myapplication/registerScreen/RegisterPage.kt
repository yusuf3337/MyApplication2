package com.example.myapplication.registerScreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.myapplication.R

class RegisterPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_page)
    }

    fun backLogin(view : View){
        val intent = Intent(this@RegisterPage, Personalinformation::class.java)
        startActivity(intent)
}
    }

