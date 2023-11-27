package com.example.myapplication.evArkadasi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.myapplication.EvArkadasiSingelton
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

        val intent = intent
    }
    fun evArkadasi2Devam(view: View) {
        var tercihEdilenCinsiyet = binding.tercihEdilenCinsiyet.text.toString()
        var tercihEdilenYas = binding.tercihEdilenYas.text.toString()
        var alkolKullanimi = binding.alkolKullanimi.text.toString()
        var sigaraKullanimi = binding.sigaraKullanimi.text.toString()
        var tercihEdilenUniversiteBolumu = binding.tercihEdilenUniversiteBolumu.text.toString()
        var tercihEdilenUyruk = binding.tercihEdilenUyruk.text.toString()

        if (tercihEdilenCinsiyet.isEmpty() || tercihEdilenYas.isEmpty() || alkolKullanimi.isEmpty() || sigaraKullanimi.isEmpty() || tercihEdilenUniversiteBolumu.isEmpty() || tercihEdilenUyruk.isEmpty()){
            //showAlertDialog("Hata!", "Lütfen Alanları Doldurunuz")
        }else {
            // Alanlar doluysa kayıt işlemini başlat
            EvArkadasiSingelton.tercihEdilenCinsiyet = tercihEdilenCinsiyet
            EvArkadasiSingelton.tercihEdilenYas = tercihEdilenYas
            EvArkadasiSingelton.alkolKullanimi = alkolKullanimi
            EvArkadasiSingelton.sigaraKullanimi = sigaraKullanimi
            EvArkadasiSingelton.tercihEdilenUniversiteBolumu = tercihEdilenUniversiteBolumu
            EvArkadasiSingelton.tercihEdilenUyruk = tercihEdilenUyruk

            //Aidat yok !! Depozito

            if (EvArkadasiSingelton.tercihEdilenCinsiyet != "" && EvArkadasiSingelton.tercihEdilenYas != "" &&
                EvArkadasiSingelton.alkolKullanimi != "" && EvArkadasiSingelton.sigaraKullanimi != "" &&
                EvArkadasiSingelton.tercihEdilenUniversiteBolumu != "" && EvArkadasiSingelton.tercihEdilenUyruk != ""
                ){
                val intent = Intent(this, evArkadasiThree::class.java)
                startActivity(intent)
            }else{
                //showAlertDialog("hata", "singelton veri yok")
            }
        }
    }
}