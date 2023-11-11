package com.example.myapplication.Informations

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import com.example.myapplication.R
import com.example.myapplication.Register3
import com.example.myapplication.SallerHomeSingelton
import com.example.myapplication.SallerHomeSingelton.kategori
import com.example.myapplication.Singelton
import com.example.myapplication.databinding.ActivityAdInformationOneBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage
import java.util.UUID


class AdInformationOne : AppCompatActivity() {
    private lateinit var binding: ActivityAdInformationOneBinding

    private var kategori :String? = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdInformationOneBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val intent = intent
        kategori = intent.getStringExtra("Kategori")

    }

    fun goAdInformation2(view: View) {
        val ilanBasligi = binding.ilanBasligi.text.toString()
        val fiyat = binding.fiyat.text.toString()
        val m2 = binding.m2.text.toString()
        val odaSayisi = binding.odaSayisi.text.toString()
        val binaYasi = binding.binaYasi.text.toString()
        val banyoSayisi = binding.banyoSayisi.text.toString()
        val balkon = binding.balkon.text.toString()
        val esyali = binding.esyali.text.toString()

        if (ilanBasligi.isEmpty() || fiyat.isEmpty() || m2.isEmpty() || odaSayisi.isEmpty() || binaYasi.isEmpty() || banyoSayisi.isEmpty() || balkon.isEmpty() || esyali.isEmpty()) {
            //showAlertDialog("Hata!", "Lütfen Alanları Doldurunuz")
        } else {
            // Alanlar doluysa kayıt işlemini başlat
            SallerHomeSingelton.ilanBasligi = ilanBasligi
            SallerHomeSingelton.fiyat = fiyat
            SallerHomeSingelton.m2 = m2
            SallerHomeSingelton.odaSayisi = odaSayisi
            SallerHomeSingelton.binaYasi = binaYasi
            SallerHomeSingelton.banyoSayisi = banyoSayisi
            SallerHomeSingelton.balkon = balkon
            SallerHomeSingelton.esyali = esyali
            SallerHomeSingelton.kategori = kategori

            if (SallerHomeSingelton.ilanBasligi != "" && SallerHomeSingelton.fiyat != "" &&
                SallerHomeSingelton.m2 != "" && SallerHomeSingelton.odaSayisi != "" &&
                SallerHomeSingelton.binaYasi != "" && SallerHomeSingelton.banyoSayisi != "" &&
                SallerHomeSingelton.balkon != "" && SallerHomeSingelton.esyali != ""
            ) {
                val intent = Intent(this, AdInformationTwo::class.java)
                startActivity(intent) // startActivity fonksiyonunu ContextCompat.startActivity olarak değiştirin
            } else {
               // showAlertDialog("hata", "singleton veri yok ")
            }



        }
    }

    }




