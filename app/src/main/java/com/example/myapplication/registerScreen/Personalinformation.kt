package com.example.myapplication.registerScreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.myapplication.R

class Personalinformation : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personalinformation)
    }
    fun register3(view : View){
        val intent = Intent(this,Register3::class.java)
        startActivity(intent)
}
}