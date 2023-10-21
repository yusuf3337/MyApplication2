package com.example.myapplication.registerScreen

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.myapplication.Singelton
import com.example.myapplication.databinding.ActivityPersonalinformationBinding

class Personalinformation : AppCompatActivity() {
    private lateinit var binding: ActivityPersonalinformationBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPersonalinformationBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun register3(view: View) {
        val name = binding.name.text.toString()
        val surname = binding.surname.text.toString()
        val phoneNumber = binding.phoneNumber.text.toString()
        val age = binding.age.text.toString()
        val universityDepartment = binding.universityDepartment.text.toString()
        val universityYear = binding.universityYears.text.toString()

        if (name.isEmpty() || surname.isEmpty() || phoneNumber.isEmpty() || age.isEmpty() || universityDepartment.isEmpty() || universityYear.isEmpty()) {
            showAlertDialog("Hata!", "Lütfen Alanları Doldurunuz")
        } else {
            // Alanlar doluysa kayıt işlemini başlat
            Singelton.name = name
            Singelton.phone = phoneNumber
            Singelton.age = age
            Singelton.universitydepartment = universityDepartment
            Singelton.universityyear = universityYear

            val intent = Intent(this, Register3::class.java)
            startActivity(intent)
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
