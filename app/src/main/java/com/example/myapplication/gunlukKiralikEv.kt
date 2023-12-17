package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.myapplication.Informations.AdInformationTwo

class gunlukKiralikEv : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gunluk_kiralik_ev)
    }
    fun gunlukEv(view : View){
        val intent = Intent(this, gunlukKiralikEv2::class.java)
        startActivity(intent)
    }
}