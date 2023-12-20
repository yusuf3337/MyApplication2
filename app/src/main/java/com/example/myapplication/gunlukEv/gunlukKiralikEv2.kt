package com.example.myapplication.gunlukEv

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.myapplication.R

class gunlukKiralikEv2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gunluk_kiralik_ev2)
    }
    fun gunlukEv2(view : View){
        val intent = Intent(this, gunlukKiralikEv3::class.java)
        startActivity(intent)
    }
}