package com.example.myapplication.registerScreen

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.myapplication.Singelton
import com.example.myapplication.databinding.ActivityRegisterPageBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterPage : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterPageBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth
    }

    fun backLogin(view: View) {
        val username = binding.username.text.toString()
        val email = binding.email.text.toString()
        val password = binding.password.text.toString()
        val confirmPassword = binding.confirmPassword.text.toString()

        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showAlertDialog("Hata!", "Lütfen Alanları Doldurunuz")
        } else {
            // Alanlar doluysa kayıt işlemini başlat
            Singelton.username = username
            Singelton.email = email
            Singelton.password = confirmPassword

             val intent = Intent(this,Personalinformation::class.java)
        startActivity(intent)
        }
    }

    private fun registerUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener(this) {
                showAlertDialog("Başarılı", "Üye oldunuz")
            }
            .addOnFailureListener {
                showAlertDialog("Hata", "Üyelik oluşturulamadı: ${it.message}")
            }
    }

    private fun showAlertDialog(title: String, message: String) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("Tamam") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }
}
