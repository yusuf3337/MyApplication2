package com.example.myapplication

import HomeRecyclerAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.FragmentHomeBinding
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import java.util.Date

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class Home : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private var binding: FragmentHomeBinding? = null
    private lateinit var homeRecyclerAdapter: HomeRecyclerAdapter
    private val homeList = mutableListOf<IlanBilgileri>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.homeRecylerView?.layoutManager = LinearLayoutManager(requireContext())
        binding?.homeRecylerView?.adapter = homeRecyclerAdapter
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Home().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    fun QuerySnapshot?.toIlanBilgileriList(): List<IlanBilgileri> {
        val ilanlar = mutableListOf<IlanBilgileri>()

        this?.documents?.forEach { document ->
            val ilanBilgileri = document.toIlanBilgileri()
            ilanBilgileri?.let { ilanlar.add(it) }
        }

        return ilanlar
    }

    fun DocumentSnapshot.toIlanBilgileri(): IlanBilgileri? {
        val docData = data

        return try {
            IlanBilgileri(
                ilanBasligi = docData?.get("ilanBasligi") as? String ?: "",
                ilanFiyat = docData?.get("ilanFiyat") as? String ?: "",
                evM2 = docData?.get("evM2") as? String ?: "",
                evBanyoSayisi = docData?.get("evBanyoSayisi") as? String ?: "",
                evBalkon = docData?.get("evBalkon") as? String ?: "",
                evEsya = docData?.get("evEsya") as? String ?: "",
                evBinaYasi = docData?.get("evBinaYasi") as? String ?: "",
                evOdaSayisi = docData?.get("evOdaSayisi") as? String ?: "",
                ilanAciklamasi = docData?.get("ilanAciklamsi") as? String ?: "",
                binaKatsayisi = docData?.get("evBinaKatSayisi") as? String ?: "",
                binaAidatTutari = docData?.get("binaAidatTutari") as? String ?: "",
                evDepozitoTutari = docData?.get("evDepozitoTutari") as? String ?: "",
                evbulunduguKat = docData?.get("evBulunduguKat") as? String ?: "",
                evCephe = docData?.get("evCephe") as? String ?: "",
                evUlasim = docData?.get("evUlasim") as? String ?: "",
                evOgrenciyeUygun = docData?.get("evOgrenciyeUygun") as? String ?: "",
                evIsitma = docData?.get("evIsitma") as? String ?: "",
                evAcikAdres = docData?.get("evAcikAdresi") as? String ?: "",
                evinSehiri = docData?.get("evinSehiri") as? String ?: "",
                documentID = docData?.get("documentID") as? String ?: "",
                ilanKategorisi = docData?.get("ilanKategorisi") as? String ?: "",
                ilanNumarasi = (docData?.get("ilanNumarasi") as? Long)?.toInt() ?: 0,
                ilanYuklemeTarihi = (docData?.get("ilanYuklemeTarihi") as? Timestamp)?.toDate()
                    ?: Date(),
                photoURLArray = docData?.get("photoURLs") as? List<String> ?: emptyList(),
                isActive = (docData?.get("isActive") as? Long)?.toInt() ?: 0,
                ilanParaBirimi = docData?.get("ilanParaBirimi") as? String ?: "",
                ilanKiraMinSuresi = docData?.get("ilanKiraMinSuresi") as? String ?: "",
                aylikAidatParaBirimi = docData?.get("aylikAidatParaBirimi") as? String ?: "",
                depozitoParaBirimi = docData?.get("depozitoParaBirimi") as? String ?: "",
                doping = (docData?.get("doping") as? Long)?.toInt() ?: 0,
                acilAcilDoping = (docData?.get("acilAcilDoping") as? Long)?.toInt() ?: 0,
                dopingCerceve = (docData?.get("dopingCerceve") as? Long)?.toInt() ?: 0,
                dopingYazisi = docData?.get("dopingYazisi") as? String ?: "",
                gosterimSayisi = (docData?.get("gosterimSayisi") as? Long)?.toInt() ?: 0
            )
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    data class IlanBilgileri(
        val ilanBasligi: String,
        val ilanFiyat: String,
        val evM2: String,
        val evBanyoSayisi: String,
        val evBalkon: String,
        val evEsya: String,
        val evBinaYasi: String,
        val evOdaSayisi: String,
        val ilanAciklamasi: String,
        val binaKatsayisi: String,
        val binaAidatTutari: String,
        val evDepozitoTutari: String,
        val evbulunduguKat: String,
        val evCephe: String,
        val evUlasim: String,
        val evOgrenciyeUygun: String,
        val evIsitma: String,
        val evAcikAdres: String,
        val evinSehiri: String,
        val documentID: String,
        val ilanKategorisi: String,
        val ilanNumarasi: Int,
        val ilanYuklemeTarihi: Date,
        val photoURLArray: List<String>,
        val isActive: Int,
        val ilanParaBirimi: String,
        val ilanKiraMinSuresi: String,
        val aylikAidatParaBirimi: String,
        val depozitoParaBirimi: String,
        val doping: Int,
        val acilAcilDoping: Int,
        val dopingCerceve: Int,
        val dopingYazisi: String,
        val gosterimSayisi: Int
    )
}
