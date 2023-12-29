package com.example.myapplication.registerScreen

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.myapplication.Register3
import com.example.myapplication.Singelton.Singelton
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
        val gender = binding.gender.text.toString()

        if (name.isEmpty() || surname.isEmpty() || phoneNumber.isEmpty() || age.isEmpty() || universityDepartment.isEmpty() || universityYear.isEmpty() || gender.isEmpty()) {
            showAlertDialog("Hata!", "Lütfen Alanları Doldurunuz")
        } else {
            // Alanlar doluysa kayıt işlemini başlat
            Singelton.name = name
            Singelton.surname = surname
            Singelton.phone = phoneNumber
            Singelton.age = age
            Singelton.universitydepartment = universityDepartment
            Singelton.universityyear = universityYear
            Singelton.gender = gender

            if ( Singelton.name != "" && Singelton.surname != "" && Singelton.phone != "" && Singelton.age != "" && Singelton.universitydepartment != "" && Singelton.universityyear != "" && Singelton.gender != ""){
                val intent = Intent(this, Register3::class.java)
                startActivity(intent)
            }else{
                showAlertDialog("hata", "singleton veri yok ")
            }



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


    private fun showAlertSingelton(title: String, message: String) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("Tamam") { dialog, _ ->

            }
            .create()
            .show()
    }
}

