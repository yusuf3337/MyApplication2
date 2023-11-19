package com.example.myapplication.Informations

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.myapplication.R
import com.example.myapplication.SallerHomeSingelton
import com.example.myapplication.databinding.ActivityAdInformationThreeBinding
import com.example.myapplication.databinding.ActivityRegister3Binding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage


class AdInformationThree : AppCompatActivity() {
    private lateinit var binding: ActivityAdInformationThreeBinding

    private lateinit var auth: FirebaseAuth
    private lateinit var firebaseDB: FirebaseFirestore
    private lateinit var firebaseStorage: FirebaseStorage

    var secilenAcikAdres: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityAdInformationThreeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        auth = Firebase.auth
        firebaseDB = Firebase.firestore
        firebaseStorage = Firebase.storage



    }

    fun goAdInformation4(view: View) {
        if (binding.acikAdres.text.toString() == "") {
            showAlertDialog("Hata!", "Lütfen Alanları Doldurunuz")
        } else {
            SallerHomeSingelton.acikAdres = binding.acikAdres.text.toString()
            val intent = Intent(this, AdInformationFour::class.java)
            startActivity(intent)
        }
    }





    private fun showAlertDialog(title: String, message: String) {
        if (!isFinishing) {
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
}