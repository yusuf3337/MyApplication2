package com.example.myapplication.loginScreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.myapplication.R
import com.example.myapplication.passwordScreen.ForgotPassword
import com.example.myapplication.registerScreen.RegisterPage

class LoginPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)

        val forgotpassword = findViewById<TextView>(R.id.loginPageTextView2)
forgotpassword.setOnClickListener {
    // Yeni bir sayfaya geçiş Intent'i oluşturun
    val intent = Intent(this, ForgotPassword::class.java)

    // İstediğiniz ekstra verileri intent ile iletebilirsiniz (isteğe bağlı)
    // intent.putExtra("key", "value")

    // Yeni sayfaya geçiş yapın
    startActivity(intent)
}
        val gotoRegister = findViewById<TextView>(R.id.gotoRegister)
gotoRegister.setOnClickListener {
    // Yeni bir sayfaya geçiş Intent'i oluşturun
    val intent = Intent(this, RegisterPage::class.java)

    // İstediğiniz ekstra verileri intent ile iletebilirsiniz (isteğe bağlı)
    // intent.putExtra("key", "value")

    // Yeni sayfaya geçiş yapın
    startActivity(intent)
}

    }
}