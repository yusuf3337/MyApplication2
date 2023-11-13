package com.example.myapplication.Informations

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityAdInformationThreeBinding


class AdInformationThree : AppCompatActivity() {
    private lateinit var binding: ActivityAdInformationThreeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ad_information_three)
        binding = ActivityAdInformationThreeBinding.inflate(layoutInflater)
        val view = binding.root
    }

    fun goAdInformation4(view: View) {
        val intent = Intent(this, AdInformationFour::class.java)
        startActivity(intent)
    }
}