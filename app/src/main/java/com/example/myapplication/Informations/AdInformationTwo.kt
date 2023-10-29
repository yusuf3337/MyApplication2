package com.example.myapplication.Informations

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityAdInformationOneBinding
import com.example.myapplication.databinding.ActivityAdInformationTwoBinding

class AdInformationTwo : AppCompatActivity() {
    private lateinit var binding: ActivityAdInformationTwoBinding

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
        spinnerKatSayisi()
        spinnerBulunduguKat()
        spinnerKullanimDurumu()
        spinnerCephe()
        spinnerUlasim()
        spinnerIsıtma()
        spinnerOgrenciyeUygun()
    }

    fun spinnerKatSayisi() {
        val spinnerKatSayisi = binding.katSayisiSpinner
        val katSayisi = resources.getStringArray(R.array.KatSayısı)
        if (spinnerKatSayisi != null) {
            val adapter = ArrayAdapter(this@AdInformationTwo, R.layout.custom_spinner_item, katSayisi)

            // Spinner açılır menüsünün görünümünü ayarlayın
            adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice)

            spinnerKatSayisi.adapter = adapter

            spinnerKatSayisi.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                    if (position != 0) {
                        secilenKatSayisi = katSayisi[position]

                        // Secilen Ogeyi yusuf Atayabilirsin bir degeskene!
                        binding.katSayisi.setText(secilenKatSayisi)
                    }
                }
                override fun onNothingSelected(p0: AdapterView<*>?) {
                    // Hiçbir şey seçilmediğinde yapılacak işlem!!!! YSF
                }
            }
        }
    }


    fun spinnerBulunduguKat() {
        val spinnerBulunduguKat= binding.bulunduguKatSpinner
        val bulunduguKat = resources.getStringArray(R.array.BulunduğuKat)

        if (spinnerBulunduguKat != null) {
            val adapter = ArrayAdapter(this@AdInformationTwo, R.layout.custom_spinner_item, bulunduguKat)

            // Spinner açılır menüsünün görünümünü ayarlayın
            adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice)

            spinnerBulunduguKat.adapter = adapter

            spinnerBulunduguKat.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                    if (position != 0) {
                        secilenBulunduguKat = bulunduguKat[position]

                        // Secilen Ogeyi yusuf Atayabilirsin bir degeskene!
                        binding.bulunduguKat.setText(secilenBulunduguKat)
                    }
                }
                override fun onNothingSelected(p0: AdapterView<*>?) {
                    // Hiçbir şey seçilmediğinde yapılacak işlem!!!! YSF
                }
            }
        }
    }

    fun spinnerKullanimDurumu() {
        val spinnerKullanimDurumu= binding.kullanimDurumuSpinner
        val kullanimDurumu = resources.getStringArray(R.array.KullanımDurumu)

        if (spinnerKullanimDurumu != null) {
            val adapter = ArrayAdapter(this@AdInformationTwo, R.layout.custom_spinner_item, kullanimDurumu)

            // Spinner açılır menüsünün görünümünü ayarlayın
            adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice)

            spinnerKullanimDurumu.adapter = adapter

            spinnerKullanimDurumu.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                    if (position != 0) {
                        secilenKullanimDurumu = kullanimDurumu[position]

                        // Secilen Ogeyi yusuf Atayabilirsin bir degeskene!
                        binding.kullanimDurumu.setText(secilenKullanimDurumu)
                    }
                }
                override fun onNothingSelected(p0: AdapterView<*>?) {
                    // Hiçbir şey seçilmediğinde yapılacak işlem!!!! YSF
                }
            }
        }
    }

    fun spinnerCephe() {
        val spinnerCephe= binding.cepheSpinner
        val Cephe = resources.getStringArray(R.array.KullanımDurumu)

        if (spinnerCephe != null) {
            val adapter = ArrayAdapter(this@AdInformationTwo, R.layout.custom_spinner_item, Cephe)

            // Spinner açılır menüsünün görünümünü ayarlayın
            adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice)

            spinnerCephe.adapter = adapter

            spinnerCephe.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                    if (position != 0) {
                        secilenCehpe = Cephe[position]

                        // Secilen Ogeyi yusuf Atayabilirsin bir degeskene!
                        binding.cephe.setText(secilenCehpe)
                    }
                }
                override fun onNothingSelected(p0: AdapterView<*>?) {
                    // Hiçbir şey seçilmediğinde yapılacak işlem!!!! YSF
                }
            }
        }
    }

    fun spinnerUlasim() {
        val spinnerUlasim = binding.ulasimSpinner
        val ulasim = resources.getStringArray(R.array.Ulasim)

        if (spinnerUlasim != null) {
            val adapter = ArrayAdapter(this@AdInformationTwo, R.layout.custom_spinner_item, ulasim)

            // Spinner açılır menüsünün görünümünü ayarlayın
            adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice)

            spinnerUlasim.adapter = adapter

            spinnerUlasim.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                    if (position != 0) {
                        secilenUlasim = ulasim[position]

                        // Secilen Ogeyi yusuf Atayabilirsin bir degeskene!
                        binding.ulasim.setText(secilenUlasim)
                    }
                }
                override fun onNothingSelected(p0: AdapterView<*>?) {
                    // Hiçbir şey seçilmediğinde yapılacak işlem!!!! YSF
                }
            }
        }
    }

    fun spinnerIsıtma() {
        val spinnerIsitma= binding.isitmaSpinner
        val Isitma = resources.getStringArray(R.array.Isıtma)

        if (spinnerIsitma != null) {
            val adapter = ArrayAdapter(this@AdInformationTwo, R.layout.custom_spinner_item, Isitma)

            // Spinner açılır menüsünün görünümünü ayarlayın
            adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice)

            spinnerIsitma.adapter = adapter

            spinnerIsitma.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                    if (position != 0) {
                        secilenIsıtma = Isitma[position]

                        // Secilen Ogeyi yusuf Atayabilirsin bir degeskene!
                        binding.isitma.setText(secilenIsıtma)
                    }
                }
                override fun onNothingSelected(p0: AdapterView<*>?) {
                    // Hiçbir şey seçilmediğinde yapılacak işlem!!!! YSF
                }
            }
        }
    }

    fun spinnerOgrenciyeUygun() {
        val spinnerOgrenciyeUygun= binding.ogrenciyeUygunSpinner
        val OgrenciyeUygun = resources.getStringArray(R.array.ÖğrenciyeUygun)

        if (spinnerOgrenciyeUygun != null) {
            val adapter = ArrayAdapter(this@AdInformationTwo, R.layout.custom_spinner_item, OgrenciyeUygun)

            // Spinner açılır menüsünün görünümünü ayarlayın
            adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice)

            spinnerOgrenciyeUygun.adapter = adapter

            spinnerOgrenciyeUygun.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                    if (position != 0) {
                        secilenOgrenciyeUygun = OgrenciyeUygun[position]

                        // Secilen Ogeyi yusuf Atayabilirsin bir degeskene!
                        binding.ogrenciyeUygun.setText(secilenOgrenciyeUygun)
                    }
                }
                override fun onNothingSelected(p0: AdapterView<*>?) {
                    // Hiçbir şey seçilmediğinde yapılacak işlem!!!! YSF
                }
            }
        }
    }


    }

