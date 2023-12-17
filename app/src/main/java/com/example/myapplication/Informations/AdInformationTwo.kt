package com.example.myapplication.Informations

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import com.example.myapplication.R
import com.example.myapplication.SallerHomeSingelton
import com.example.myapplication.databinding.ActivityAdInformationTwoBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage

import java.util.UUID

class AdInformationTwo : AppCompatActivity() {
    private lateinit var binding: ActivityAdInformationTwoBinding

    private lateinit var auth: FirebaseAuth
    private lateinit var firebaseDB: FirebaseFirestore
    private lateinit var firebaseStorage: FirebaseStorage

    var secilenKatSayisi: String = ""
    var secilenBulunduguKat: String = ""
    var secilenKullanimDurumu: String = ""
    var secilenCehpe: String = ""
    var secilenUlasim: String = ""
    var secilenIsıtma: String = ""
    var secilenOgrenciyeUygun: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ad_information_two)
        binding = ActivityAdInformationTwoBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = Firebase.auth
        firebaseDB = Firebase.firestore
        firebaseStorage = Firebase.storage

    }

    fun goAdInformation3(view: View) {
        val katSayisi = binding.katSayisi.text.toString()
        val bulunduguKat = binding.bulunduguKat.text.toString()
        val kullanimDurumu = binding.kullanimDurumu.text.toString()
        val cephe = binding.cephe.text.toString()
        val ulasim = binding.ulasim.text.toString()
        val isitma = binding.isitma.text.toString()
        val ogrenciyeUygun = binding.ogrenciyeUygun.text.toString()
        val ilanAciklamasi = binding.ilanAciklamasi.text.toString()
        val ilanSehiri = binding.ilanSehiri.text.toString()

        if (katSayisi.isEmpty() || bulunduguKat.isEmpty() || kullanimDurumu.isEmpty() || cephe.isEmpty() || ulasim.isEmpty() || isitma.isEmpty() || ogrenciyeUygun.isEmpty() || ilanSehiri.isEmpty()) {
            // showAlertDialog("Hata!", "Lütfen Alanları Doldurunuz")
        } else {
            // Alanlar doluysa kayıt işlemini başlat
            SallerHomeSingelton.binaKatSayisi = katSayisi
            SallerHomeSingelton.bulunduguKatSayisi = bulunduguKat
            SallerHomeSingelton.kullanimDurumu = kullanimDurumu
            SallerHomeSingelton.cephe = cephe
            SallerHomeSingelton.ulasim = ulasim
            SallerHomeSingelton.isitmaVarMiNedir = isitma
            SallerHomeSingelton.ogrenciyeUygunmudur = ogrenciyeUygun
            SallerHomeSingelton.ilanAciklamasi = ilanAciklamasi
            SallerHomeSingelton.ilanSehiri = ilanSehiri

            if (SallerHomeSingelton.ilanBasligi != "" && SallerHomeSingelton.ilanfiyat != "" &&
                SallerHomeSingelton.evM2 != "" && SallerHomeSingelton.odaSayisi != "" &&
                SallerHomeSingelton.binaYasi != "" && SallerHomeSingelton.banyoSyisi != "" &&
                SallerHomeSingelton.balkonVarMi != "" && SallerHomeSingelton.esyaliMi != ""
            ) {
                val intent = Intent(this, AdInformationThree::class.java)
                startActivity(intent) // startActivity fonksiyonunu ContextCompat.startActivity olarak değiştirin
            } else {
                //showAlertDialog("hata", "singleton veri yok ")
            }

        }
    }

}



