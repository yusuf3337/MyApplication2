package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage

class Onizleme : AppCompatActivity() {


    private lateinit var auth: FirebaseAuth
    private lateinit var firebaseDB: FirebaseFirestore
    private lateinit var firebaseStorage: FirebaseStorage
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onizleme)


        auth = Firebase.auth
        firebaseDB = Firebase.firestore
        firebaseStorage = Firebase.storage
    }






    private fun saveUserDataToFirestore(imageReference: StorageReference) {
        imageReference.downloadUrl
            .addOnSuccessListener { uri ->
                val downloadURL = uri.toString()
                val userMap = hashMapOf<String, Any>()
                userMap.put("downloadURL", downloadURL)
                userMap.put("İlan Başlığı", SallerHomeSingelton.ilanBasligi!!)
                userMap.put("Fiyat", SallerHomeSingelton.ilanfiyat!!)
                userMap.put("M2(Net)", SallerHomeSingelton.evM2!!)
                userMap.put("Oda Sayısı", SallerHomeSingelton.odaSayisi!!)
                userMap.put("Bina Yaşı", SallerHomeSingelton.binaYasi!!)
                userMap.put("Banyo Sayısı", SallerHomeSingelton.banyoSyisi!!)
                userMap.put("Balkon", SallerHomeSingelton.balkonVarMi!!)
                userMap.put("CreateDateUser", com.google.firebase.Timestamp.now())
                userMap.put("Eşyalı", SallerHomeSingelton.esyaliMi!!)
                userMap.put("Kat Sayısı", SallerHomeSingelton.binaKatSayisi!!)
                userMap.put("Bulunduğu Kat", SallerHomeSingelton.bulunduguKatSayisi!!)
                userMap.put("Kullanım Durumu", SallerHomeSingelton.kullanimDurumu!!)
                userMap.put("Cephe", SallerHomeSingelton.cephe!!)
                userMap.put("Ulaşım", SallerHomeSingelton.ulasim!!)
                userMap.put("Isıtıma", SallerHomeSingelton.isitmaVarMiNedir!!)
                userMap.put("Öğrenciye Uygun", SallerHomeSingelton.ogrenciyeUygunmudur!!)

            }

    }
}