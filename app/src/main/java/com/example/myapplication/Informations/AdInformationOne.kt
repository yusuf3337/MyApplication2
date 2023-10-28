package com.example.myapplication.Informations

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityAdInformationOneBinding
import com.example.myapplication.databinding.ActivityPersonalinformationBinding

class AdInformationOne : AppCompatActivity() {
    private lateinit var binding: ActivityAdInformationOneBinding

    var secilanOdaSayisi: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ad_information_one)
        binding = ActivityAdInformationOneBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        spinnerOdaSayi()

    }

    fun spinnerOdaSayi() {
        val spinneroda= binding.odaSayisiSpp
        val sehirler = resources.getStringArray(R.array.OdaSayisi)

        if (spinneroda != null) {
            val adapter = ArrayAdapter(this@AdInformationOne, R.layout.custom_spinner_item, sehirler)

            // Spinner açılır menüsünün görünümünü ayarlayın
            adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice)

            spinneroda.adapter = adapter

            spinneroda.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                    if (position != 0) {
                    secilanOdaSayisi = sehirler[position]

                        // Secilen Ogeyi yusuf Atayabilirsin bir degeskene!

                    }
                }
                override fun onNothingSelected(p0: AdapterView<*>?) {
                    // Hiçbir şey seçilmediğinde yapılacak işlem!!!! YSF
                }
            }
        }
    }





}
