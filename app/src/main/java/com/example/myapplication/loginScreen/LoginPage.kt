package com.example.myapplication.loginScreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.example.myapplication.Home
import com.example.myapplication.R
import com.example.myapplication.menu.LoginMenu
import com.example.myapplication.passwordScreen.ForgotPassword
import com.example.myapplication.registerScreen.RegisterPage

class LoginPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)

        val forgotPassword = findViewById<TextView>(R.id.loginPageTextView2)
        forgotPassword.setOnClickListener {
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
            startActivity(intent)
        }
    }

    fun loginPage(view: View) {
        // Giriş sayfasını işlemek için buraya gerekli kodu ekleyin
    }

    fun goMenu(view: View) {
        val intent = Intent(this, LoginMenu::class.java)
        startActivity(intent)
        finish()
    }
}
