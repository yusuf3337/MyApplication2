package com.example.myapplication.evArkadasi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.R

class evArkadasiThree : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ev_arkadasi_three)
        val intent = Intent(this, evArkadasiFour::class.java)
        startActivity(intent)
    }

}