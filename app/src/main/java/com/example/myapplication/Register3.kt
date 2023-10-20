package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.myapplication.databinding.ActivityRegister3Binding
import com.example.myapplication.loginScreen.LoginPage

class Register3 : AppCompatActivity() {

    private lateinit var binding: ActivityRegister3Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegister3Binding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val singletonName = Singelton.name
        val singletonSurname = Singelton.surname
        val singletenPhone = Singelton.phone
        val singletonEmail = Singelton.email

        if (singletonName != null && singletonSurname != null && singletenPhone != null && singletonEmail != null ) {
            binding.nameSingelton.setText(singletonName)
            binding.surnameSingelton.setText(singletonSurname)
            binding.phoneSingelton.setText(singletenPhone)
            binding.emailSingelton.setText(singletonEmail)
        } else {

        }

        // EditText öğesini düzenlenebilir hale getirin
        binding.nameSingelton.isEnabled = true
    }

    fun goSuccessPage(view: View) {
        val intent = Intent(this, LoginPage::class.java)
        startActivity(intent)
    }
}
