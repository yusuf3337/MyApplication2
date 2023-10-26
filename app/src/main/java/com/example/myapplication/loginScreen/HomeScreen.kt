package com.example.myapplication.loginScreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.myapplication.Add
import com.example.myapplication.Home
import com.example.myapplication.Profile
import com.example.myapplication.R
import com.example.myapplication.Search
import com.example.myapplication.Settings
import com.example.myapplication.databinding.ActivityHomeScreenBinding

class HomeScreen : AppCompatActivity() {

    private lateinit var binding: ActivityHomeScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(Home())

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.Home -> replaceFragment(Home())
                R.id.Search -> replaceFragment(Search())
                R.id.Add -> replaceFragment(Add())
                R.id.Profile -> replaceFragment(Profile())
                R.id.Settings -> replaceFragment(Settings())
            }
            true
        }


    }

    private fun replaceFragment(fragment: Fragment){

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,fragment)
        fragmentTransaction.commit()


    }
}