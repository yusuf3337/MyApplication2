package com.example.myapplication

import IlanBilgileri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.adapter.IlanlarAdapter
import com.google.firebase.firestore.FirebaseFirestore

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

val ilanlarKarma = mutableListOf<IlanBilgileri>()


/**
 * A simple [Fragment] subclass.
 * Use the [Settings.newInstance] factory method to
 * create an instance of this fragment.
 */
class Home : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)



    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("Home", "BASLATILDI")
        firebaseGetData()

        val recyclerView: RecyclerView = view.findViewById(R.id.homeRecylerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = IlanlarAdapter(ilanlarKarma)
    }

    fun firebaseGetData() {
        Log.d("Home", "FirebaseGetData GELDI")
        val database = FirebaseFirestore.getInstance()
        val ilanlarCollection = database.collection("ilanlar")

        ilanlarCollection.addSnapshotListener { snapshot, error ->
            if (error != null) {
                Log.e("Home", "Hata oluştu: ${error.localizedMessage}")
            } else {
                snapshot?.let { documents ->
                    ilanlarKarma.clear()
                    for (document in documents) {
                        val docData = document.data
                        val ilanBilgileri = createIlanBilgileri(docData)
                        if (ilanBilgileri != null) {
                            ilanlarKarma.add(ilanBilgileri)
                            Log.e("ilanBilgileraaai", "${ilanBilgileri.ilanBasligi}")
                        } else {
                            Log.e("ilanBilgileri", "NULL GELDI}")
                        }
                        Log.d("Home", "AD SOYAD: ${ilanBilgileri?.adSoyad}")
                    }

                    ilanlarKarma.shuffle()

                    // RecyclerView adapter'ını güncelle
                    val recyclerView: RecyclerView = view?.findViewById(R.id.homeRecylerView)!!
                    recyclerView.adapter?.notifyDataSetChanged()

                } ?: run {
                    Log.d("Home", "Hiç doküman bulunamadı.")
                }
            }
        }
    }


    private fun createIlanBilgileri(docData: Map<String, Any>): IlanBilgileri? {
        // Map içeriğini IlanBilgileri sınıfına uygun şekilde doldurun
        val ilanBasligi = docData["ilanBasligi"] as? String
        val ilanFiyat = docData["ilanFiyat"] as? String
        // Diğer alanları da uygun şekilde doldurun

        // IlanBilgileri sınıfını oluşturun
        return if (ilanBasligi != null && ilanFiyat != null /* Diğer gerekli alanlar */) {
            IlanBilgileri(
                ilanBasligi = ilanBasligi,
                ilanFiyat = ilanFiyat,
                // Diğer alanları da buraya ekleyin
            )
        } else {
            null
        }
    }




    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Settings.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Settings().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}