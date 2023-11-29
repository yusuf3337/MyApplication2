package com.example.myapplication

import java.util.Date

class IlanBilgileri {
        var ilanBasligi: String? = null
        var ilanFiyat:  String? = null
        var evM2:  String? = null
        var evBanyoSayisi:  String? = null
        var evBalkon:  String? = null
        var evEsya:  String? = null
        var evBinaYasi:  String? = null
        var evOdaSayisi:  String? = null
        var ilanAciklamasi: String? = null
        var binaKatsayisi:  String? = null
        var binaAidatTutari:  String? = null
        var evDepozitoTutari:  String? = null
        var evbulunduguKat:  String? = null
        var evCephe: String? = null
        var evUlasim:  String? = null
        var evOgrenciyeUygun:  String? = null
        var evIsitma:  String? = null
        var evAcikAdres:  String? = null
        var evinSehiri:  String? = null
        var documentID:  String? = null
        var ilanKategorisi: String? = null
        var ilanNumarasi: Int? = null
        var ilanYuklemeTarihi: Date = Date()
        var photoURLArray: List<String> = emptyList()
        var isActive: Int? = null

        var ilanParaBirimi: String? = null
        var ilanKiraMinSuresi: String? = null
        var aylikAidatParaBirimi: String? = null
        var depozitoParaBirimi: String? = null
        var doping: Int? = null
        var acilAcilDoping: Int? = null
        var dopingCerceve: Int? = null
        var dopingYazisi: String? = null
        var gosterimSayisi: Int? = null

        // Yeni Eklenen Kısım

        var adSoyad: String? = null
        var telefonNumarasi: String? = null
        var ePosta: String? = null

        var okulSecimi: String? = null
        var yurtSecimi: String? = null
        var odaTipi: String? = null
        var odaDevirSecenekleri: String? = null
        var tercihEdilenCinsiyet: String? = null
        var internetVarMi: String? = null
        var girisCikisSaatleri: String? = null
        var blok: String? = null
        var kizErkekKarisikMi: String? = null

}
