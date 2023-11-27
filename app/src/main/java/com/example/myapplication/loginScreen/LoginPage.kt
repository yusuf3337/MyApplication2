package com.example.myapplication.loginScreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityAdInformationFourBinding
import com.example.myapplication.databinding.ActivityLoginPageBinding
import com.example.myapplication.passwordScreen.ForgotPassword
import com.example.myapplication.registerScreen.RegisterPage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import android.widget.Toast


class LoginPage : AppCompatActivity() {

    private lateinit var binding: ActivityLoginPageBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var firebaseDB: FirebaseFirestore
    private lateinit var firebaseStorage: FirebaseStorage
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginPageBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

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
        auth = FirebaseAuth.getInstance()
        firebaseDB = FirebaseFirestore.getInstance()
        firebaseStorage = FirebaseStorage.getInstance()
    }

    fun loginPage(view: View) {
        // Giriş sayfasını işlemek için buraya gerekli kodu ekleyin
    }

    fun goMenu(view: View) {
        val email = binding.editTextEmail.text.toString()
        val password = binding.editTextPassword.text.toString()

        if (email.isNotEmpty() && password.isNotEmpty()) {
            // Firebase ile e-posta ve şifre ile giriş yapma
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Giriş başarılı
                        val user = auth.currentUser
                        if (user != null && user.isEmailVerified) {
                            // E-posta onayı yapılmışsa ana ekrana yönlendir
                            val intent = Intent(this@LoginPage, HomeScreen::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            // E-posta onayı yapılmamışsa kullanıcıya uyarı ver
                            Toast.makeText(
                                this@LoginPage,
                                "E-posta adresinizi onaylayınız.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        // Giriş başarısız
                        Toast.makeText(
                            this@LoginPage,
                            "Giriş başarısız. E-posta veya şifrenizi kontrol ediniz.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        } else {
            Toast.makeText(
                this@LoginPage,
                "E-posta ve şifre boş olmamalıdır.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}