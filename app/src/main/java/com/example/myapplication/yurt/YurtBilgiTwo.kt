package com.example.myapplication.yurt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.myapplication.Informations.AdInformationThree
import com.example.myapplication.R
import com.example.myapplication.YurtDevirSingleton
import com.example.myapplication.databinding.ActivityYurtBilgiTwoBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage

class YurtBilgiTwo : AppCompatActivity() {
    private lateinit var binding: ActivityYurtBilgiTwoBinding

    private lateinit var auth: FirebaseAuth
    private lateinit var firebaseDB: FirebaseFirestore
    private lateinit var firebaseStorage: FirebaseStorage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_yurt_bilgi_two)
        binding = ActivityYurtBilgiTwoBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        auth = Firebase.auth
        firebaseDB = Firebase.firestore
        firebaseStorage = Firebase.storage
    }

    fun yurtDevamButton2(view: View) {
        val elektirikUcret = binding.elektirikUcret.text.toString()
        val internetDurumu = binding.internetDurumu.text.toString()
        val yurtGirisCikisSaatlari = binding.yurtGirisCikisSaatlari.text.toString()
        val yurtBlokVarmi = binding.yurtBlokVarmi.text.toString()
        val kizErkekKarisikMi = binding.kizErkekKarisikMi.text.toString()
        val ilanAciklamasi = binding.yurtilanAciklamasi.text.toString()

        if (elektirikUcret.isEmpty() || internetDurumu.isEmpty() || yurtGirisCikisSaatlari.isEmpty() || yurtBlokVarmi.isEmpty() || kizErkekKarisikMi.isEmpty() || ilanAciklamasi.isEmpty()) {
            // showAlertDialog("Hata!", "Lütfen Alanları Doldurunuz")
        } else {
            // Alanlar doluysa kayıt işlemini başlat
            YurtDevirSingleton.elektirikUcret = elektirikUcret
            YurtDevirSingleton.internetDurumu = internetDurumu
            YurtDevirSingleton.yurtGirisCikisSaatlari = yurtGirisCikisSaatlari
            YurtDevirSingleton.yurtBlokVarmi = yurtBlokVarmi
            YurtDevirSingleton.kizErkekKarisikMi = kizErkekKarisikMi
            YurtDevirSingleton.yurtilanAciklamasi = ilanAciklamasi

            if (YurtDevirSingleton.elektirikUcret != "" && YurtDevirSingleton.internetDurumu != "" &&
                YurtDevirSingleton.yurtGirisCikisSaatlari != "" && YurtDevirSingleton.yurtBlokVarmi != "" &&
                YurtDevirSingleton.kizErkekKarisikMi != "" && YurtDevirSingleton.yurtilanAciklamasi != ""
            ) {
                val intent = Intent(this, YurtBilgiThree::class.java)
                startActivity(intent) // startActivity fonksiyonunu ContextCompat.startActivity olarak değiştirin
            } else {
                //showAlertDialog("hata", "singleton veri yok ")
            }
        }
    }
}
