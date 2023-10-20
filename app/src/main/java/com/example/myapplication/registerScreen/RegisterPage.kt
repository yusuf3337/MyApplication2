package com.example.myapplication.registerScreen


import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.myapplication.databinding.ActivityRegisterPageBinding
import android.annotation.SuppressLint
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterPage : AppCompatActivity() {
     private lateinit var binding: ActivityRegisterPageBinding
     private lateinit var auth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterPageBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        auth = Firebase.auth
    }

    fun backLogin(view: View) {
    val username = binding.username.text.toString()
    val email = binding.email.text.toString()
    val password = binding.password.text.toString()
    val confirmPassword = binding.confirmPassword.text.toString()


        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
        // Alanlar boşsa uyarıyı göster
        alertSetup("Hata!", "Lütfen Alanları Doldurunuz")
    }   else {
        auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(this) {
            alertSetup("Başarılı","Üye oldunuz")
        }.addOnFailureListener {

        }
        // Alanlar doluysa sayfa geçişi yap ve dalgayı göster
       /* val intent = Intent(this, Personalinformation::class.java)
        startActivity(intent)
        finish()*/
        // Burada dalgayı gösterme işlemini ekleyebilirsiniz
      }

    }

        fun alertSetup(title: String, message: String) {
        val alertDialog = AlertDialog.Builder(this)

        // Uyarının başlık ve mesajını ayarlayın
        alertDialog.setTitle(title)
        alertDialog.setMessage(message)

        // Pozitif buton eklemek (örneğin "Tamam" düğmesi)
        alertDialog.setPositiveButton("Tamam") { dialog, which ->
            // "Tamam" düğmesine tıklandığında yapılacak işlemler buraya gelebilir
            dialog.dismiss() // Uyarıyı kapat
        }

        // Uyarıyı göster
        val dialog = alertDialog.create()
        dialog.show()
    }
}




