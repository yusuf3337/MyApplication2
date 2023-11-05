package com.example.myapplication.Informations

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.myapplication.R
import com.example.myapplication.Register3
import com.example.myapplication.Singelton
import com.example.myapplication.databinding.ActivityAdInformationOneBinding
import com.example.myapplication.databinding.ActivityPersonalinformationBinding

class AdInformationOne : AppCompatActivity() {
    private lateinit var binding: ActivityAdInformationOneBinding

    var secilenOdaSayisi: String = ""
    var secilenBinaYasi: String = ""
    var secilenBanyoSayisi: String = ""
    var secilenBalkon: String = ""
    var secilenEsyali: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ad_information_one)
        binding = ActivityAdInformationOneBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        /*spinnerOdaSayi()
        spinnerBinaYasi()
        spinnerBanyoSayisi()
        spinnerBalkon()
        spinnerEsyali()*/
    }

    /*fun spinnerOdaSayi() {
        val spinneroda= binding.odaSayisiSpinner
        val odaSayisi = resources.getStringArray(R.array.OdaSayısı)

        if (spinneroda != null) {
            val adapter = ArrayAdapter(this@AdInformationOne, R.layout.custom_spinner_item, odaSayisi)

            // Spinner açılır menüsünün görünümünü ayarlayın
            adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice)

            spinneroda.adapter = adapter

            spinneroda.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                    if (position != 0) {
                    secilenOdaSayisi = odaSayisi[position]

                        // Secilen Ogeyi yusuf Atayabilirsin bir degeskene!
                        binding.odaSayisi.setText(secilenOdaSayisi)
                    }
                }
                override fun onNothingSelected(p0: AdapterView<*>?) {
                    // Hiçbir şey seçilmediğinde yapılacak işlem!!!! YSF
                }
            }
        }
    }

    fun spinnerBinaYasi() {
        val spinnerBinaYasi= binding.binaYasiSpinner
        val binaYasi = resources.getStringArray(R.array.BinaYaşı)

        if (spinnerBinaYasi != null) {
            val adapter = ArrayAdapter(this@AdInformationOne, R.layout.custom_spinner_item, binaYasi)

            // Spinner açılır menüsünün görünümünü ayarlayın
            adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice)

            spinnerBinaYasi.adapter = adapter

            spinnerBinaYasi.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                    if (position != 0) {
                        secilenBinaYasi = binaYasi[position]

                        // Secilen Ogeyi yusuf Atayabilirsin bir degeskene!
                        binding.binaYasi.setText(secilenBinaYasi)
                    }
                }
                override fun onNothingSelected(p0: AdapterView<*>?) {
                    // Hiçbir şey seçilmediğinde yapılacak işlem!!!! YSF
                }
            }
        }
    }

    fun spinnerBanyoSayisi() {
        val spinnerBanyoSayisi= binding.banyoSayisiSpinner
        val banyoSayisi = resources.getStringArray(R.array.BanyoSayısı)

        if (spinnerBanyoSayisi != null) {
            val adapter = ArrayAdapter(this@AdInformationOne, R.layout.custom_spinner_item, banyoSayisi)

            // Spinner açılır menüsünün görünümünü ayarlayın
            adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice)

            spinnerBanyoSayisi.adapter = adapter

            spinnerBanyoSayisi.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                    if (position != 0) {
                        secilenBanyoSayisi = banyoSayisi[position]

                        // Secilen Ogeyi yusuf Atayabilirsin bir degeskene!
                        binding.banyoSayisi.setText(secilenBanyoSayisi)
                    }
                }
                override fun onNothingSelected(p0: AdapterView<*>?) {
                    // Hiçbir şey seçilmediğinde yapılacak işlem!!!! YSF
                }
            }
        }
    }

    fun spinnerBalkon() {
        val spinnerBalkon= binding.balkonSpinner
        val balkon = resources.getStringArray(R.array.Balkon)

        if (spinnerBalkon != null) {
            val adapter = ArrayAdapter(this@AdInformationOne, R.layout.custom_spinner_item, balkon)

            // Spinner açılır menüsünün görünümünü ayarlayın
            adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice)

            spinnerBalkon.adapter = adapter

            spinnerBalkon.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                    if (position != 0) {
                        secilenBalkon = balkon[position]

                        // Secilen Ogeyi yusuf Atayabilirsin bir degeskene!
                        binding.balkon.setText(secilenBalkon)
                    }
                }
                override fun onNothingSelected(p0: AdapterView<*>?) {
                    // Hiçbir şey seçilmediğinde yapılacak işlem!!!! YSF
                }
            }
        }
    }

    fun spinnerEsyali() {
        val spinnerEsyali= binding.esyaliSpinner
        val esyali = resources.getStringArray(R.array.Eşyalı)

        if (spinnerEsyali != null) {
            val adapter = ArrayAdapter(this@AdInformationOne, R.layout.custom_spinner_item, esyali)

            // Spinner açılır menüsünün görünümünü ayarlayın
            adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice)

            spinnerEsyali.adapter = adapter

            spinnerEsyali.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                    if (position != 0) {
                        secilenEsyali = esyali[position]

                        // Secilen Ogeyi yusuf Atayabilirsin bir degeskene!
                        binding.esyali.setText(secilenEsyali)
                    }
                }
                override fun onNothingSelected(p0: AdapterView<*>?) {
                    // Hiçbir şey seçilmediğinde yapılacak işlem!!!! YSF
                }
            }
        }
    }
*/
    fun goAdInformation2(view: View){
    val intent = Intent(this@AdInformationOne, AdInformationTwo::class.java)
    startActivity(intent)
    }



}
