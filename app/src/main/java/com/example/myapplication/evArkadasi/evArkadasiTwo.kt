package com.example.myapplication.evArkadasi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.myapplication.Singelton.EvArkSingleton
import com.example.myapplication.databinding.ActivityEvArkadasiTwoBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage


class evArkadasiTwo : AppCompatActivity() {
    private lateinit var binding: ActivityEvArkadasiTwoBinding

    private lateinit var auth: FirebaseAuth
    private lateinit var firebaseDB: FirebaseFirestore
    private lateinit var firebaseStorage: FirebaseStorage

    private var kategori: String? = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEvArkadasiTwoBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = FirebaseAuth.getInstance()
        firebaseDB = FirebaseFirestore.getInstance()
        firebaseStorage = FirebaseStorage.getInstance()

    }
    fun evArkadasi2Devam(view: View) {
        var tercihEdilenCinsiyet = binding.tercihEdilenCinsiyet.text.toString()
        var tercihEdilenYasAraligi = binding.tercihEdilenYas.text.toString()
        var alkolKullanimi = binding.alkolKullanimi.text.toString()
        var sigaraKullanimi = binding.sigaraKullanimi.text.toString()
        var universiteBolumu = binding.tercihEdilenUniversiteBolumu.text.toString()
        var tercihEdilenUyruk = binding.tercihEdilenUyruk.text.toString()

        if (tercihEdilenCinsiyet.isEmpty() || tercihEdilenYasAraligi.isEmpty() || alkolKullanimi.isEmpty() || sigaraKullanimi.isEmpty() || universiteBolumu.isEmpty() || tercihEdilenUyruk.isEmpty()){
            //showAlertDialog("Hata!", "Lütfen Alanları Doldurunuz")
        }else {
            // Alanlar doluysa kayıt işlemini başlat
            EvArkSingleton.tercihEdilenCinsiyet = tercihEdilenCinsiyet
            EvArkSingleton.tercihEdilenYasAraligi = tercihEdilenYasAraligi
            EvArkSingleton.alkolKullanimi = alkolKullanimi
            EvArkSingleton.sigaraKullanimi = sigaraKullanimi
            EvArkSingleton.universiteBolumu = universiteBolumu
            EvArkSingleton.tercihEdilenUyruk = tercihEdilenUyruk

            //Aidat yok !! Depozito

            if (EvArkSingleton.tercihEdilenCinsiyet != "" && EvArkSingleton.tercihEdilenYasAraligi != "" &&
                EvArkSingleton.alkolKullanimi != "" && EvArkSingleton.sigaraKullanimi != "" &&
                EvArkSingleton.universiteBolumu != "" && EvArkSingleton.tercihEdilenUyruk != ""
                ){
                val intent = Intent(this, evArkadasiThree::class.java)
                startActivity(intent)
            }else{
                //showAlertDialog("hata", "singelton veri yok")
            }
        }
    }
}