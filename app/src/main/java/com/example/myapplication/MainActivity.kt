package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    fun loginPage(view : View){
        val intent = Intent(this@MainActivity,LoginPage::class.java)
        startActivity(intent)
    }

    fun registerPage(view: View){
        val intent = Intent(this@MainActivity,RegisterPage::class.java)
        startActivity(intent)
    }

}