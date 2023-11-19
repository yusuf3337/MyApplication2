package com.example.myapplication.Informations

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.myapplication.R
import com.example.myapplication.SallerHomeSingelton
import com.example.myapplication.databinding.ActivityAdInformationThreeBinding
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
        setContentView(R.layout.activity_ad_information_three)
        binding = ActivityAdInformationThreeBinding.inflate(layoutInflater)
        val view = binding.root

        auth = Firebase.auth
        firebaseDB = Firebase.firestore
        firebaseStorage = Firebase.storage

    }

    fun goAdInformation4(view: View) {
        val acikAdres = binding.acikAdres.text.toString()

        if (acikAdres.isEmpty()) {
            //showAlerDialog("Hata!", "Lütfen Alanları Doldurunuz")
        } else {
            SallerHomeSingelton.acikAdres = acikAdres

            if (SallerHomeSingelton.ilanBasligi != "" && SallerHomeSingelton.ilanfiyat != "" &&
                SallerHomeSingelton.evM2 != "" && SallerHomeSingelton.odaSayisi != "" &&
                SallerHomeSingelton.binaYasi != "" && SallerHomeSingelton.banyoSyisi != "" &&
                SallerHomeSingelton.balkonVarMi != "" && SallerHomeSingelton.esyaliMi != ""
            ) {

                val intent = Intent(this, AdInformationFour::class.java)
                startActivity(intent)
            } else {
                //showAlertDialog("hata", "singleton veri yok ")
            }
        }
    }
}