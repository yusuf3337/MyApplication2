package com.example.myapplication.yurt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.myapplication.Singelton.YurtDevirSingleton
import com.example.myapplication.databinding.ActivityYurtBilgiOneBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class YurtBilgiOne : AppCompatActivity() {
    private lateinit var binding: ActivityYurtBilgiOneBinding

    private lateinit var auth: FirebaseAuth
    private lateinit var firebaseDB: FirebaseFirestore
    private lateinit var firebaseStorage: FirebaseStorage

    private var kategori: String? = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityYurtBilgiOneBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = FirebaseAuth.getInstance()
        firebaseDB = FirebaseFirestore.getInstance()
        firebaseStorage = FirebaseStorage.getInstance()

        val intent = intent
        kategori = intent.getStringExtra("Kategori")
    }
    fun yurtDevamButton(view: View) {
        val yurtilanBasligi = binding.yurtilanBasligi.text.toString()
        val okulSecimi = binding.okulSecimi.text.toString()
        val yurtSecimi = binding.yurtSecimi.text.toString()
        val odaTipiSecimi = binding.odaTipiSecimi.text.toString()
        val odaDevirSecenekleri = binding.odaDevirSecenekleri.text.toString()
        val tercihEdilenCinsiyet = binding.tercihEdilenCinsiyet.text.toString()

        if (yurtilanBasligi.isEmpty() || okulSecimi.isEmpty() || yurtSecimi.isEmpty() || odaTipiSecimi.isEmpty() || odaDevirSecenekleri.isEmpty() || tercihEdilenCinsiyet.isEmpty()){
            //showAlertDialog("Hata!", "Lütfen Alanları Doldurunuz")
        }else {
            // Alanlar doluysa kayıt işlemini başlat
            YurtDevirSingleton.yurtilanBasligi = yurtilanBasligi
            YurtDevirSingleton.okulSecimi = yurtilanBasligi
            YurtDevirSingleton.yurtSecimi = yurtilanBasligi
            YurtDevirSingleton.odaTipiSecimi = yurtilanBasligi
            YurtDevirSingleton.odaDevirSecenekleri = yurtilanBasligi
            YurtDevirSingleton.tercihEdilenCinsiyet = yurtilanBasligi

            // Aidat yok !! Depozito

            if (YurtDevirSingleton.yurtilanBasligi != "" && YurtDevirSingleton.okulSecimi != "" &&
                YurtDevirSingleton.yurtSecimi != "" && YurtDevirSingleton.odaTipiSecimi != "" &&
                YurtDevirSingleton.odaDevirSecenekleri != "" && YurtDevirSingleton.tercihEdilenCinsiyet != ""
                ){
                val intent = Intent(this, YurtBilgiTwo::class.java)
                startActivity(intent)
            }else {
                // showAlertDialog("hata", "singleton veri yok ")
            }
        }
    }
}