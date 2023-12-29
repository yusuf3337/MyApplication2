package com.example.myapplication.Informations

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.Singelton.SallerHomeSingelton
import com.example.myapplication.databinding.ActivityAdInformationOneBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class AdInformationOne : AppCompatActivity() {
    private lateinit var binding: ActivityAdInformationOneBinding

    private lateinit var auth: FirebaseAuth
    private lateinit var firebaseDB: FirebaseFirestore
    private lateinit var firebaseStorage: FirebaseStorage

    private var kategori: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdInformationOneBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = FirebaseAuth.getInstance()
        firebaseDB = FirebaseFirestore.getInstance()
        firebaseStorage = FirebaseStorage.getInstance()

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
        val binaAidatTutari = binding.binaAidatTutari.text.toString()
        val evDepozitoTutari = binding.evDepozitoTutari.text.toString()

        if (ilanBasligi.isEmpty() || fiyat.isEmpty() || m2.isEmpty() || odaSayisi.isEmpty() || binaYasi.isEmpty() || banyoSayisi.isEmpty() || balkon.isEmpty() || esyali.isEmpty() || binaAidatTutari.isEmpty() || evDepozitoTutari.isEmpty()) {
            showAlertDialog("Hata!", "Lütfen Alanları Doldurunuz")
        } else {
            // Alanlar doluysa kayıt işlemini başlat
            SallerHomeSingelton.ilanBasligi = ilanBasligi
            SallerHomeSingelton.ilanfiyat = fiyat
            SallerHomeSingelton.evM2 = m2
            SallerHomeSingelton.odaSayisi = odaSayisi
            SallerHomeSingelton.binaYasi = binaYasi
            SallerHomeSingelton.banyoSyisi = banyoSayisi
            SallerHomeSingelton.balkonVarMi = balkon
            SallerHomeSingelton.esyaliMi = esyali
            SallerHomeSingelton.binaAidatTutari = binaAidatTutari
            SallerHomeSingelton.evDepozitoTutari = evDepozitoTutari
            SallerHomeSingelton.ilanKategorisi = kategori

            // Aidat yok !! Depozito
            if (SallerHomeSingelton.ilanBasligi != "" && SallerHomeSingelton.ilanfiyat != "" &&
                SallerHomeSingelton.evM2 != "" && SallerHomeSingelton.odaSayisi != "" &&
                SallerHomeSingelton.binaYasi != "" && SallerHomeSingelton.banyoSyisi != "" &&
                SallerHomeSingelton.balkonVarMi != "" && SallerHomeSingelton.esyaliMi != "" &&
                SallerHomeSingelton.binaAidatTutari != "" && SallerHomeSingelton.evDepozitoTutari != ""
            ) {
                val intent = Intent(this, AdInformationTwo::class.java)
                startActivity(intent)
            } else {
                // showAlertDialog("hata", "singleton veri yok ")
            }
        }
    }

    private fun showAlertDialog(title: String, message: String) {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle(title)
        alertDialogBuilder.setMessage(message)
        alertDialogBuilder.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, _ ->
            dialog.dismiss()
        })
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
}
